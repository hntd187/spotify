package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.{Spotify, response => r}

/**
  * Get a single track.
  * https://developer.spotify.com/web-api/get-track/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Track(id: String, market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[r.Track] {
  override protected val request = base.withPath(s"$TR/$id").withQueryParameter("market", market)

  def getAudioFeatures: AudioFeatures = AudioFeatures(id)
  def getAudioAnalysis: AudioAnalysis = AudioAnalysis(id)
}
