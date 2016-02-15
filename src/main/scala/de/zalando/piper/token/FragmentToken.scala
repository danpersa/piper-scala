package de.zalando.piper.token

import java.util.UUID

import com.ning.http.client.uri.Uri

case class FragmentToken(val uri: Uri) extends Token {
  val id = UUID.randomUUID().toString

  override def description: String = "fragment token"
}

object FragmentToken {
  def apply(uri: String) = new FragmentToken(Uri.create(uri))
}
