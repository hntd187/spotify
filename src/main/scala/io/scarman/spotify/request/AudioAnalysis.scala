package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

/**
  * This queries for the audio analysis of a track. Beware, this usually returns a lot of data.
  * https://developer.spotify.com/web-api/get-audio-analysis/
  *
  * @param id
  * @param spotify
  */
case class AudioAnalysis(id: String)(implicit spotify: Spotify) extends HttpRequest[response.AudioAnalysis] {
  lazy protected val request = Endpoints.AudioAnalysis / id
}
