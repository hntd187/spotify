package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response
import sttp.client._
import sttp.model._

case class CurrentlyPlaying()(implicit auth: Authorization, backend: Backend) extends HttpRequest[response.CurrentlyPlaying] {

  lazy protected val reqUri: Uri = uri"$base/me/player/currently-playing"

}
