package io.scarman.spotify.request

import com.softwaremill.sttp.SttpBackend
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.{Spotify, response => r}

import scala.concurrent.Future

import com.softwaremill.sttp._

/**
  * Get a single track.
  * https://developer.spotify.com/web-api/get-track/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Track(id: String, market: String = "ES")(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[r.Track] {

  lazy protected val reqUri = uri"$base$TR/$id".param("market", market)

  def getAudioFeatures: AudioFeatures = AudioFeatures(id)
  def getAudioAnalysis: AudioAnalysis = AudioAnalysis(id)
}
