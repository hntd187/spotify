package io.scarman.spotify.request

import io.scarman.spotify.*
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*

import scala.concurrent.ExecutionContext

case class TopTracks(id: String, country: String = "ES")(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[response.Tracks] {
  lazy protected val reqUri = uri"$base$AR/$id/top-tracks".addParam("country", country)
}
