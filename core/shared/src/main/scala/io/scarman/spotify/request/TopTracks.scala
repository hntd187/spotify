package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

case class TopTracks(id: String, country: String = "ES")(implicit auth: Authorization, backend: Backend)
    extends HttpRequest[response.Tracks] {
  lazy protected val reqUri = uri"$base$AR/$id/top-tracks".param("country", country)
}
