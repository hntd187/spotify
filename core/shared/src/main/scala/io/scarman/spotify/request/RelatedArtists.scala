package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

case class RelatedArtists(id: String)(implicit auth: Authorization, backend: Backend) extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/$id/related-artists"
}
