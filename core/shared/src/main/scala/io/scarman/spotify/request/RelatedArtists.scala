package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}

import scala.concurrent.Future

case class RelatedArtists(id: String)(implicit auth: Authorization, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/$id/related-artists"
}
