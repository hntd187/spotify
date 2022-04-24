package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** This queries for the audio analysis of a track. Beware, this usually returns a lot of data.
  * https://developer.spotify.com/web-api/get-audio-analysis/
  *
  * @param id
  */
case class AudioAnalysis(id: String)(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[response.AudioAnalysis] {
  lazy protected val reqUri = uri"$base$AA/$id"
}
