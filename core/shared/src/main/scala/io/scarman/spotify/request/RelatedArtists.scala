package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._
import monix.execution.Scheduler

case class RelatedArtists(id: String)(implicit spotify: Spotify, scheduler: Scheduler) extends HttpRequest[response.Artists] {
  lazy protected val request = base.withPath(s"$AR/$id/related-artists")
}
