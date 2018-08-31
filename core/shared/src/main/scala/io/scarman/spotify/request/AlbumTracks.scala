package io.scarman.spotify.request

import io.scarman.spotify.Spotify
import io.scarman.spotify.http._

/**
  * The tracks on an album.
  * https://developer.spotify.com/web-api/get-albums-tracks/
  *
  * @param id
  * @param market
  * @param limit
  * @param offset
  * @param spotify
  */
case class AlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 5, paging: Boolean = false)(
    implicit spotify: Spotify,
    val scheduler: Scheduler)
    extends HttpRequest[TrackPage] {

  lazy protected val request = if (!paging) {
    base
      .withPath(s"$AB/$id/tracks")
      .withQueryParameter("market", market)
      .withQueryParameter("limit", limit.toString)
      .withQueryParameter("offset", offset.toString)
  } else {
    HR(id)
  }

  def nextPage(): Option[AlbumTracks] = {
    for {
      p <- this()().next
    } yield AlbumTracks(p, paging = true)
  }
}
