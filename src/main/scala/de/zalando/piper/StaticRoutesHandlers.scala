package de.zalando.piper

import java.nio.ByteBuffer

import de.zalando.piper.template.{BeardTemplateService, TemplateParser}
import de.zalando.piper.token.{FragmentToken, StaticTextToken}
import io.undertow.server.{HttpHandler, HttpServerExchange}
import io.undertow.util.HttpString
import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive.Ack.Continue
import monifu.reactive.subjects.ReplaySubject
import monifu.reactive.{Ack, Observable, Observer}
import org.slf4j.LoggerFactory

import scala.concurrent.Future

/**
 * @author dpersa
 */
object StaticRoutesHandlers {
  val logger = LoggerFactory.getLogger(this.getClass)
  
  val httpClient = HttpClient.client

  val piperHandler = new HttpHandler {
    
    override def handleRequest(exchange: HttpServerExchange) = {
      
      val sampleTemplate = BeardTemplateService.compiledTemplate("sample3")
      
      val parsedTemplate = TemplateParser.parse(sampleTemplate)
      
      // start executing requests in parallel
      val streams = parsedTemplate.context.uriMap.map { case (id, url) =>
        val stream = ReplaySubject[ByteBuffer]()
        logger.debug(s"get url: ${url.toUrl}")
        httpClient.prepareGet(url.toUrl).execute(MonixStreamingAsyncHandler(stream))
        (id -> stream)
      }
      
      val templateStreams: Seq[Observable[ByteBuffer]] = parsedTemplate.tokens.map {
        case StaticTextToken(text) => {
          val stream = ReplaySubject[ByteBuffer]()
          stream.onNext(ByteBuffer.wrap(text.getBytes(/* encoding */)) )
          stream.onComplete()
          logger.debug(s"Emit static text token: ${text}")
          stream
        }
        case token @ FragmentToken(_) => {
          val stream: Observable[ByteBuffer] = streams.get(token.id).get
          logger.debug(s"Emit Fragment token: ${token.uri}")
          stream
        }
      }
      
      val concat = Observable.concat(templateStreams:_*)

      exchange.getResponseHeaders().add(new HttpString("Content-Type"), "text/html")
      exchange.startBlocking()
      exchange.dispatch()

      concat.subscribe(new Observer[ByteBuffer] {
        override def onError(ex: Throwable): Unit = ???

        override def onComplete(): Unit =  {
          //logger.debug("onComplete")
          exchange.endExchange()
        }

        override def onNext(elem: ByteBuffer): Future[Ack] = {
          //logger.debug("onNext")
          exchange.getOutputStream.write(elem.array())
          Future(Continue)
        }
      })
    }
  }

  val pureHandler: HttpHandler =
    (exchange: HttpServerExchange) => exchange.getResponseSender().send("{\"hello\": \"world\"}")

  def textHandler(text: String): HttpHandler =
    (exchange: HttpServerExchange) => {
      exchange.getResponseHeaders().add(new HttpString("Content-Type"), "text/html")
      exchange.getResponseSender().send(s"<div>$text</div>")
    }
}
