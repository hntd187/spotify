package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

/**
  * Get some interesting metadata about a specific track
  * https://developer.spotify.com/web-api/track-endpoints/
  *
  * @param id
  * @param spotify
  */
case class AudioFeatures(id: String)(implicit auth: Authorization, backend: Backend) extends HttpRequest[response.AudioFeatures] {
  lazy protected val reqUri = uri"$base$AF/$id"
}
