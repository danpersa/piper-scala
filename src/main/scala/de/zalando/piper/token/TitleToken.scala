package de.zalando.piper.token

import scala.collection.mutable.MutableList

class TitleToken extends UninitializedToken with Token {
  override def init(pipes: MutableList[Placeholder]) = this

  override def description: String = "Scompositor"
}

object TitleToken {
  def apply() = new TitleToken()
}