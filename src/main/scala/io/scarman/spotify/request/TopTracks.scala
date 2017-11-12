package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.response

case class TopTracks(id: String, country: String = "ES")(implicit spotify: Spotify) extends HttpRequest[response.Tracks] {
  lazy protected val request: Req = (Endpoints.Artists / id / "top-tracks").addQueryParameter("country", country)
}
