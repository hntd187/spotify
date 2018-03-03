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
case class AlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 5)(implicit spotify: Spotify)
    extends HttpRequest[TrackPage]
    with PagingRequest[TrackPage] {

  lazy protected val request = base
    .withPath(s"$AB/$id/tracks")
    .withQueryParameter("market", market)
    .withQueryParameter("limit", limit.toString)
    .withQueryParameter("offset", offset.toString)
}
