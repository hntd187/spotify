package io.scarman.spotify.request

import com.softwaremill.sttp.SttpBackend
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import com.softwaremill.sttp._

import scala.concurrent.Future

case class RelatedArtists(id: String)(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/$id/related-artists"
}
