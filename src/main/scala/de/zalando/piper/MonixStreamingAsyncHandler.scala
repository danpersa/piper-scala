package de.zalando.piper

import java.nio.ByteBuffer

import com.ning.http.client.AsyncHandler.STATE
import com.ning.http.client.{AsyncHandler, HttpResponseBodyPart, HttpResponseHeaders, HttpResponseStatus}
import monifu.reactive.Observable
import monifu.reactive.subjects.ReplaySubject
import org.slf4j.LoggerFactory

/**
 * @author dpersa
 */
case class MonixStreamingAsyncHandler(subject: ReplaySubject[ByteBuffer]) extends AsyncHandler[Observable[ByteBuffer]] {

  val logger = LoggerFactory.getLogger(this.getClass)

  override def onCompleted(): Observable[ByteBuffer] = {
    logger.debug("Monix on complete")
    subject.onComplete()
    subject
  }

  override def onThrowable(t: Throwable) = {}

  override def onBodyPartReceived(bodyPart: HttpResponseBodyPart): STATE = {
    logger.debug("Monix on body part received")
    subject.onNext(bodyPart.getBodyByteBuffer())
    
    STATE.CONTINUE
  }

  override def onStatusReceived(responseStatus: HttpResponseStatus): STATE = {
    STATE.CONTINUE
  }

  override def onHeadersReceived(headers: HttpResponseHeaders): STATE = STATE.CONTINUE
}
