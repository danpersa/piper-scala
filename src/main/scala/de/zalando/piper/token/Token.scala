package de.zalando.piper.token

import com.ning.http.client.uri.Uri
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.language.postfixOps
import scala.util.Random

trait UninitializedToken {
  val queryExtractor = "(\\?.*)" r
  val r = new Random(0L)

  def init(pipes: mutable.MutableList[Placeholder]): Token
}

trait Token {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def toString = s"${this.getClass.getSimpleName} ($description})"

  def description: String

}

case class FragmentScript(fragmentId: String, uri: Uri)


case class Placeholder(id: String, uri: Uri)

