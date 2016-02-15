package de.zalando.piper.template

import com.google.common.cache.{Cache, CacheBuilder}
import de.zalando.beard.ast.BeardTemplate
import de.zalando.beard.renderer.{BeardTemplateRenderer, CustomizableTemplateCompiler, FileTemplateLoader, TemplateName}

/**
  * @author dpersa
  */
object BeardTemplateService {

  private val templateLoader = new FileTemplateLoader(s"${Assets.assetsDir}/templates", ".beard.html")
  private val compiler = new CustomizableTemplateCompiler(templateLoader = templateLoader)

  private val cache: Cache[String, BeardTemplate] = CacheBuilder
    .newBuilder()
    .initialCapacity(100)
    .concurrencyLevel(10)
    .build()

  val renderer = new BeardTemplateRenderer(compiler)

  def compiledTemplate(templateName: String) = cache.get(templateName, () => {
    compiler.compile(TemplateName(templateName)).get
  })
}
