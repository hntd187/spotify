package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response as r
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** Get a single track. https://developer.spotify.com/web-api/get-track/
  *
  * @param id
  * @param market
  */
case class Track(id: String, market: String = "US")(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[r.Track] {

  lazy protected val reqUri = uri"$base$TR/$id".addParam("market", market)

  def getAudioFeatures: AudioFeatures = AudioFeatures(id)
  def getAudioAnalysis: AudioAnalysis = AudioAnalysis(id)
}
