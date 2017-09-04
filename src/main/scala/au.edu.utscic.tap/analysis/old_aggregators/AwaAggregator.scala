package au.edu.utscic.tap.services.analytics.aggregators

import akka.actor.{Actor, ActorLogging}
import akka.pattern.ask
import akka.util.Timeout
import au.edu.utscic.tap.data._

import scala.concurrent.Future
import scala.concurrent.duration._


class AwaAggregator extends Actor with ActorLogging {

  implicit val executor = context.system.dispatcher
  implicit val timeout = Timeout (30 seconds)
//  val metricsAnalyser = context.actorSelection("/user/analyticsService/metricsAnalyser")
//  val vocabAnalyser = context.actorSelection("/user/analyticsService/vocabAnalyser")
//  val syllableAnalyser = context.actorSelection("/user/analyticsService/syllableAnalyser")
//  val posAnalyser = context.actorSelection("/user/analyticsService/posAnalyser")
//  val complexityAnalyser = context.actorSelection("/user/analyticsService/complexityAnalyser")
//  val expressionAnalyser = context.actorSelection("/user/analyticsService/expressionAnalyser")
//  val xipAnalyser = context.actorSelection("/user/analyticsService/xipAnalyser")
//  val spellingAnalyser = context.actorSelection("/user/analyticsService/spellingAnalyser")

  override def preStart() = {
    log.debug("Starting Aggregator")
  }
  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.error(reason, "Restarting Aggregator due to [{}] when processing [{}]",
      reason.getMessage, message.getOrElse(""))
  }

  def receive = {
    //case text:String => sender ! aggregate(text)
    //case inputData:InputData => sender ! aggregate(inputData)
    case _ => sender ! "Invalid input"
  }
/*
  def aggregate(text:String):Future[Analytics] = aggregate(InputData("","",text.split("\n").toList))

  def aggregate(inputData:InputData):Future[Analytics] = {

    val paraAnalytics:List[Future[ParagraphAnalytics]] = inputData.text.zipWithIndex.map { case(para,idx) =>
      // For each paragraph get it's analytics
      val request = ParagraphRequest(idx+1,para) //paragraph indexes start at 1
      for {
        pm <- ask(metricsAnalyser, request).mapTo[ParagraphMetrics]
        pv <- ask(vocabAnalyser, request).mapTo[ParagraphVocab]
        ps <- ask(syllableAnalyser,request).mapTo[ParagraphSyllables]
        pc = complexity(pm,pv,ps)
        pp <- ask(posAnalyser,request).mapTo[ParagraphPosStats]
        pe <- ask(expressionAnalyser,request).mapTo[Future[ParagraphExpressions]].flatMap(identity)
        //dx <- ask(xipAnalyser,request).mapTo[ParagraphXip]
        psp <- ask(spellingAnalyser,request).mapTo[Future[ParagraphSpelling]].flatMap(identity)
      } yield ParagraphAnalytics(pm,pv,ps,pc,pp,pe,psp)
    }

    val docAnalytics:Future[DocumentAnalytics] = for {
      dm <- ask(metricsAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentMetrics]].flatMap(identity)
      dv <- ask(vocabAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentVocab]].flatMap(identity)
      ds <- ask(syllableAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentSyllables]].flatMap(identity)
      dc = complexity(dm,dv,ds)
      dp <- ask(posAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentPosStats]].flatMap(identity)
      de <- ask(expressionAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentExpressions]].flatMap(identity)
      dx <- ask(xipAnalyser,inputData).mapTo[Future[DocumentXip]].flatMap(identity)
      dsp <- ask(spellingAnalyser,DocumentRequest(Future.sequence(paraAnalytics))).mapTo[Future[DocumentSpelling]].flatMap(identity)
    } yield DocumentAnalytics(dm,dv,ds,dc,dp,de,dsp,dx)

    for {
      d <- docAnalytics
      p <- Future.sequence(paraAnalytics)
    } yield Analytics(d,p)

  }


  private def complexity(metrics:Metrics,vocab:Vocab,syllables:Syllables):Complexity = {
    val selectVocab = vocab.countVocab.map(_._2).flatten.filterNot(_.length < 4).toList
    val vocabToDocRatio = selectVocab.length / metrics.wordCount.toDouble
    val avgSentLength = metrics.wordCount / metrics.sentenceCount.toDouble
    val avgWordLength = metrics.characterCount / metrics.wordCount.toDouble
    val avgSyllables = syllables.averageSyllables
    GenericComplexity(vocabToDocRatio,avgSentLength,avgWordLength,avgSyllables)
  }
*/
}