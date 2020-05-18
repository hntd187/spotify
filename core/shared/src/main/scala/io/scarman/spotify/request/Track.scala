package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.{response => r}
import sttp.client._

/**
  * Get a single track.
  * https://developer.spotify.com/web-api/get-track/
  *
  * @param id
  * @param market
  */
case class Track(id: String, market: String = "US")(implicit auth: Authorization, backend: Backend) extends HttpRequest[r.Track] {

  lazy protected val reqUri = uri"$base$TR/$id".param("market", market)

  def getAudioFeatures: AudioFeatures = AudioFeatures(id)
  def getAudioAnalysis: AudioAnalysis = AudioAnalysis(id)
}
