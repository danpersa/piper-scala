package de.zalando.piper

import io.undertow.server.HttpServerExchange
import io.undertow.{Handlers, Undertow}
import org.slf4j.LoggerFactory

/**
 * @author dpersa
 */
object Main extends App {
  val logger = LoggerFactory.getLogger(this.getClass)
  // for the ning client
  val httpClient = HttpClient.client

  val pathHandler = Handlers.path()
    .addExactPath("/status", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.pureHandler)
  }).addExactPath("/template", (exchange: HttpServerExchange) => {
    exchange.dispatch(StaticRoutesHandlers.piperHandler)
  })

  val server = Undertow.builder
    .addHttpListener(8085, "0.0.0.0")
    .setIoThreads(400)
    .setWorkerThreads(400)
    .setHandler(pathHandler)
    .build
  server.start
}
