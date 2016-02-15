package de.zalando.piper.token

import scala.collection.mutable.MutableList

case class StaticTextToken(val text: String) extends UninitializedToken with Token {
  override def init(pipes: MutableList[Placeholder]): Token = this

  override def description: String = text
}
