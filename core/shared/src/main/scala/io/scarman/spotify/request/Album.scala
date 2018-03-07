package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest
import monix.execution.Scheduler

/**
  * An album of fire.
  * https://developer.spotify.com/web-api/get-album/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Album(id: String, market: String = "ES")(implicit spotify: Spotify, scheduler: Scheduler) extends HttpRequest[response.Album] {
  lazy protected val request = base.withPath(s"$AB/$id").withQueryParameter("market", market)

  def tracks(limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)
  }
}
