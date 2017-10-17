/*
 * Copyright 2016-2017 original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package handlers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import au.edu.utscic.tap.data._
import au.edu.utscic.tap.pipelines.materialize.TextPipeline
import au.edu.utscic.tap.pipelines.{Cleaning, Parsing}
import models.Results._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object TextAnalysisHandler {

  type Pipe[A] = Flow[String,A,NotUsed]
  type StringAnalyser = (String) => Future[StringResult]
  type StringListAnalyser = (String) => Future[StringListResult]
  type SentenceAnalyser = (String) => Future[SentencesResult]
  type VocabAnalyser = (String) => Future[VocabResult]
  type MetricsAnalyser = (String) => Future[MetricsResult]
  type ExpressionsAnalyser = (String) => Future[ExpressionsResult]
  type SyllablesAnalyser = (String) => Future[SyllablesResult]

  type StrConverter = (Future[String]) => Future[StringResult]
  type SentConverter = (Future[List[TapSentence]]) => Future[SentencesResult]
  type VocabConverter = (Future[TapVocab]) => Future[VocabResult]
  type MetricsConverter = (Future[TapMetrics]) => Future[MetricsResult]
  type ExpressionsConverter = (Future[List[TapExpressions]]) => Future[ExpressionsResult]
  type SyllablesConverter = (Future[List[TapSyllables]]) => Future[SyllablesResult]

  private implicit val asStringResult:StrConverter = (a:Future[String]) => a.map( s => StringResult(s))
  private implicit val asSentencesResult:SentConverter = (a:Future[List[TapSentence]]) => a.map(ts => SentencesResult(ts))
  private implicit val asVocabResult:VocabConverter = (a:Future[TapVocab]) => a.map(v => VocabResult(v))
  private implicit val asMetricsResult:MetricsConverter = (a:Future[TapMetrics]) => a.map(m => MetricsResult(m))
  private implicit val asExpressionsResult:ExpressionsConverter = (a:Future[List[TapExpressions]]) => a.map(e => ExpressionsResult(e))
  private implicit val asSyllablesResult:SyllablesConverter = (a:Future[List[TapSyllables]]) => a.map(s => SyllablesResult(s))

  private def analyse[A](text:String,pipeline:Pipe[A]):Future[A] = TextPipeline(text,pipeline).run

  val visible:StringAnalyser        = (text:String) => analyse[String](text,Cleaning.Pipeline.revealInvisible)
  val clean:StringAnalyser          = (text:String) => analyse[String](text,Cleaning.Pipeline.utfSimplify)
  val cleanPreserve:StringAnalyser  = (text:String) => analyse[String](text,Cleaning.Pipeline.lengthPreserve)
  val cleanMinimal:StringAnalyser   = (text:String) => analyse[String](text,Cleaning.Pipeline.utfMinimal)
  val cleanAscii:StringAnalyser     = (text:String) => analyse[String](text,Cleaning.Pipeline.asciiOnly)

  val sentences:SentenceAnalyser    = (text:String) => analyse[List[TapSentence]](text,Parsing.Pipeline.sentences)
  val vocabulary:VocabAnalyser      = (text:String) => analyse[TapVocab](text,Parsing.Pipeline.vocab)
  val metrics:MetricsAnalyser       = (text:String) => analyse[TapMetrics](text,Parsing.Pipeline.metrics)

  val expressions:ExpressionsAnalyser    = (text:String) => analyse[List[TapExpressions]](text,Parsing.Pipeline.expressions)
  val syllables:SyllablesAnalyser   = (text:String) => analyse[List[TapSyllables]](text,Parsing.Pipeline.syllables)

  val spelling:StringAnalyser       = (text:String) => dummyResult(text)
  val shape:StringAnalyser          = (text:String) => dummyResult(text)

  def dummyResult(text:String):Future[String] = Future {
    "This features is not implemented yet"
  }

  /*

  val spelling:SpellingAnalyser     = (text:String) => analyse[Spelling](text,Spelling.Pipeline.metrics)

  val shape:ShapeAnalyser           = (text:String) => analyse[Shape](text,TextShape.Pipeline.shape)
   */




}
