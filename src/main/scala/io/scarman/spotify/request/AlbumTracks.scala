package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.http.PagingRequest

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

  lazy protected val request: Req = (Endpoints.Albums / id / "tracks")
    .addQueryParameter("market", market)
    .addQueryParameter("limit", limit.toString)
    .addQueryParameter("offset", offset.toString)
}
