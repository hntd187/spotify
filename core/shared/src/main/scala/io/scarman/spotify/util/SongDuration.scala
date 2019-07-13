package io.scarman.spotify.util

import scala.concurrent.duration._

/**
  * Some API calls name the duration of the song duration_ms
  */
trait SongDuration {
  val duration_ms: Long
  def getDuration: Duration = Duration(duration_ms, MILLISECONDS)
}

/**
  * And some name it just duration, yay consistency.
  */
trait TrackDuration {
  val duration: Double
  def getDuration: Duration = Duration(duration, MILLISECONDS)
}
