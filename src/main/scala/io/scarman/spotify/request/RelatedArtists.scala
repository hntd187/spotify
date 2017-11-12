package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.response

case class RelatedArtists(id: String)(implicit spotify: Spotify) extends HttpRequest[response.Artists] {
  lazy protected val request: Req = Endpoints.Artists / id / "related-artists"
}
