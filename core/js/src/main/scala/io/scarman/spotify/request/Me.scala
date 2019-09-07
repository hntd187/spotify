package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify.response
import io.scarman.spotify.http.{Authorization, HttpRequest}

import scala.concurrent.Future

case class Me()(implicit auth: Authorization, backend: SttpBackend[Future, Nothing]) extends HttpRequest[response.User] {
  protected val reqUri: Uri = uri"$base/me"
}
