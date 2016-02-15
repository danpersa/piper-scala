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

  val pathHandler = Handlers.path()
    .addExactPath("/status", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.pureHandler)
  }).addExactPath("/fragment-1", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.textHandler("fragment-1"))
  }).addExactPath("/fragment-2", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.textHandler("fragmetn-2"))
  })

  val server = Undertow.builder
    .addHttpListener(8086, "0.0.0.0")
    .setIoThreads(400)
    .setWorkerThreads(400)
    .setHandler(pathHandler)
    .build
  server.start
}
