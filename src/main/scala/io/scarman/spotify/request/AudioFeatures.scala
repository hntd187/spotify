package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

/**
  * Get some interesting metadata about a specific track
  * https://developer.spotify.com/web-api/track-endpoints/
  *
  * @param id
  * @param spotify
  */
case class AudioFeatures(id: String)(implicit spotify: Spotify) extends HttpRequest[response.AudioFeatures] {
  lazy protected val request: Req = Endpoints.AudioFeatures / id
}
