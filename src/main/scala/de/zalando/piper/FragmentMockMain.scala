package de.zalando.piper

import io.undertow.server.HttpServerExchange
import io.undertow.{Handlers, Undertow}
import org.slf4j.LoggerFactory

/**
 * @author dpersa
 */
object FragmentMockMain extends App {
  val logger = LoggerFactory.getLogger(this.getClass)
  // for the ning client
  val httpClient = HttpClient.client

  val f1 = """fragment-1
             |fragment-1
             """".trim

  val f2 = """fragment-2
             |fragment-2
             """".trim

  val pathHandler = Handlers.path()
    .addExactPath("/status", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.pureHandler)
  }).addExactPath("/fragment-1", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.textHandler(f1))
  }).addExactPath("/fragment-2", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.textHandler(f2))
  })

  val server = Undertow.builder
    .addHttpListener(8086, "0.0.0.0")
    .setIoThreads(400)
    .setWorkerThreads(400)
    .setHandler(pathHandler)
    .build
  server.start
}
