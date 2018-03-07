package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._
import monix.execution.Scheduler

/**
  * Get some interesting metadata about a specific track
  * https://developer.spotify.com/web-api/track-endpoints/
  *
  * @param id
  * @param spotify
  */
case class AudioFeatures(id: String)(implicit spotify: Spotify, scheduler: Scheduler) extends HttpRequest[response.AudioFeatures] {
  lazy protected val request = base.withPath(s"$AF/$id")
}
