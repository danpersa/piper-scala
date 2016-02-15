package de.zalando.piper.template

import java.io.File

import com.typesafe.config.ConfigFactory

import scala.collection.immutable.Seq

/**
  * @author dpersa
  */
object Assets {

  private val config = ConfigFactory.load()
  val assetsDir = config.getString("assets.dir")
  val templatesDirs: Seq[String] = Assets.assetsSubdir("templates")


  def assetsSubdir(subdir: String) = {

    val dir = new File(s"${Assets.assetsDir}/$subdir")
    if (!dir.exists()) {
      throw new IllegalStateException(s"Assets subdir ${subdir} not found")
    }
    dir.list().toList
  }
}
