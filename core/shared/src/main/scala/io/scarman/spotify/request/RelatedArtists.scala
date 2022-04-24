package io.scarman.spotify.request

import io.scarman.spotify.*
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*

import scala.concurrent.ExecutionContext

case class RelatedArtists(id: String)(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/$id/related-artists"
}
