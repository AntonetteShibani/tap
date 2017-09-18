// Copyright (C) 2017 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package au.edu.utscic.tap.pipelines

import akka.NotUsed
import akka.stream.scaladsl.Flow
import au.edu.utscic.tap.data.{TapMetrics, TapSentence, TapVocab, TermCount}
import au.edu.utscic.tap.nlp.factorie.Annotator
import cc.factorie.app.nlp.Document

/**
  * Created by andrew@andrewresearch.net on 6/9/17.
  */
object Parsing {

  object Pipeline {
    val sentences:Flow[String,List[TapSentence],NotUsed] = makeDocument via tapSentences
    val vocab:Flow[String,TapVocab,NotUsed] = makeDocument via tapSentences via tapVocab
    val metrics:Flow[String,TapMetrics,NotUsed] = makeDocument via tapSentences via tapMetrics
  }

  val makeDocument:Flow[String,Document,NotUsed] = Flow[String].map(str => Annotator.document(str))

  val tapSentences:Flow[Document,List[TapSentence],NotUsed] =
    Flow[Document]
    .map(doc => Annotator.sentences(doc))
    .map(sentList => Annotator.tapSentences(sentList))

  val tapVocab:Flow[List[TapSentence],TapVocab,NotUsed] =
    Flow[List[TapSentence]]
      .map { lst =>
        lst.flatMap(_.tokens)
          .map(_.term.toLowerCase)
          .groupBy((term:String) => term)
          .mapValues(_.length)
      }.map { m =>
        val lst:List[TermCount] = m.toList.map{case (k,v) => TermCount(k,v)}
        TapVocab(m.size,lst)
      }

  val tapMetrics:Flow[List[TapSentence],TapMetrics,NotUsed] =
    Flow[List[TapSentence]]
    .map { lst =>
      lst.map(_.length)
    }.map( lst => TapMetrics(lst.sum))
}

/*
object Vocab {

  val document:Flow[OldTapDocument,List[OldTapSection],NotUsed] = Flow[OldTapDocument].map(_.sections)

  val sectionsVocab:Flow[List[OldTapSection],List[Map[String,Int]],NotUsed] = Flow[List[OldTapSection]].map(_.map(_.sentences.flatMap(_.tokens).groupBy((word:String) => word).mapValues(_.length)))
  val documentVocab = Flow[List[Map[String,Int]]].fold(Map[String,Int]())(_ ++ _.flatten)

  val vocabByCount = Flow[Map[String,Int]].map(_.toList.groupBy(_._2).map(wc => wc._1 -> wc._2.map(_._1)).toSeq.reverse).map(l=> ListMap(l:_*))

  val pipeline = document.via(sectionsVocab).via(documentVocab).via(vocabByCount)

}
*/