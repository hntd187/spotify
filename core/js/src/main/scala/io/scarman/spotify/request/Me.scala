package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response
import sttp.client3.*
import sttp.model.*

import scala.concurrent.ExecutionContext

case class Me()(implicit auth: Authorization, backend: Backend, ec: ExecutionContext) extends HttpRequest[response.PrivateUser] {
  lazy protected val reqUri: Uri = uri"$base/me"

  def currentlyPlaying(): CurrentlyPlaying = CurrentlyPlaying()
}
