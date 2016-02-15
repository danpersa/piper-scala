package de.zalando.piper.template

import de.zalando.beard.ast.{AttrInterpolation, BeardTemplate, HasText, Identifier, NewLine, Text, White}
import de.zalando.piper.token.{FragmentToken, StaticTextToken}

import scala.language.postfixOps

/**
 * @author dpersa
 */
object TemplateParser {

  def parse(beardTemplate: BeardTemplate): Template = {
    
    val tokens = beardTemplate.statements.map {
      case t @ (Text(_) | NewLine(_) | White(_))                   => StaticTextToken(t.asInstanceOf[HasText].text)
      case AttrInterpolation(Identifier("fragment"), attributes)   => FragmentToken(attributes.map(a => (a.key -> a.stringValue.get)).toMap.get("url").get)
      case unknown                                                 => throw new IllegalStateException(s"Unrecognized template part: $unknown")
    }
    
    val map = tokens.collect { case token : FragmentToken => (token.id -> token.uri) } toMap
    
    Template("sample", tokens, TemplateContext(map))
  }

  def urlFromContextOrAttributes(ctx: TemplateContext)(attributes: Map[String, String]): String = attributes.get("id").flatMap {
    case id => ctx.uriMap.get(id).map(u => u.toString())
  }.getOrElse(attributes("url"))
}
