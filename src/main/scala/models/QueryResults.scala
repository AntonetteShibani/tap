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

package models

import java.time.OffsetDateTime

import au.edu.utscic.tap.data.{TapMetrics, TapSentence, TapVocab}

object QueryResults {

  trait Result {
    val analytics: Any
    val timestamp: String = OffsetDateTime.now().toString
    val message: Option[String] = None
  }
  case class StringResult(analytics:String) extends Result
  case class StringListResult(analytics:List[List[String]]) extends Result
  case class SentencesResult(analytics:List[TapSentence]) extends Result
  case class VocabResult(analytics:TapVocab) extends Result
  case class MetricsResult(analytics:TapMetrics) extends Result
}