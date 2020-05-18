package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response
import sttp.client._
import sttp.model._

case class Me()(implicit auth: Authorization, backend: Backend) extends HttpRequest[response.PrivateUser] {
  lazy protected val reqUri: Uri = uri"$base/me"

  def currentlyPlaying(): CurrentlyPlaying = CurrentlyPlaying()
}
