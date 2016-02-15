package de.zalando.piper.template

import com.ning.http.client.uri.Uri

case class TemplateContext(uriMap: Map[String, Uri])

object TemplateContext {
  def apply(): TemplateContext = new TemplateContext(Map())

  def apply(i: (String, String)*): TemplateContext = new TemplateContext(i.toMap.map({
    case (k, v) => (k, Uri.create(v))
  }))
}