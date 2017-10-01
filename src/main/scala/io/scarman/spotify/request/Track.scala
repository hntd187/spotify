package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.{response => r}

/**
  * Get a single track.
  * https://developer.spotify.com/web-api/get-track/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Track(id: String, market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[r.Track] {
  override protected val request: Req = (Endpoints.Tracks / id).addQueryParameter("market", market)

  def getAudioFeatures: AudioFeatures = AudioFeatures(id)
  def getAudioAnalysis: AudioAnalysis = AudioAnalysis(id)
}
