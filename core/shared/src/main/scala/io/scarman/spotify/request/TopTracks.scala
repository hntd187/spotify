package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._
import monix.execution.Scheduler

case class TopTracks(id: String, country: String = "ES")(implicit spotify: Spotify, scheduler: Scheduler) extends HttpRequest[response.Tracks] {
  lazy protected val request = base.withPath(s"$AR/$id/top-tracks").withQueryParameter("country", country)
}
