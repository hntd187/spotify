package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}

import scala.concurrent.Future

/**
  * Get some interesting metadata about a specific track
  * https://developer.spotify.com/web-api/track-endpoints/
  *
  * @param id
  * @param spotify
  */
case class AudioFeatures(id: String)(implicit auth: Authorization, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.AudioFeatures] {
  lazy protected val reqUri = uri"$base$AF/$id"
}
