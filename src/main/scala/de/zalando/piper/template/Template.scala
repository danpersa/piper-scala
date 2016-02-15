package de.zalando.piper.template

import de.zalando.piper.token.Token
import scala.collection.immutable.Seq

case class Template(val name: String, val tokens: Seq[Token], val context: TemplateContext)
