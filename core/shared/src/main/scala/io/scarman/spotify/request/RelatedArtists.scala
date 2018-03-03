package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._

case class RelatedArtists(id: String)(implicit spotify: Spotify) extends HttpRequest[response.Artists] {
  lazy protected val request = base.withPath(s"$AR/$id/related-artists")
}
