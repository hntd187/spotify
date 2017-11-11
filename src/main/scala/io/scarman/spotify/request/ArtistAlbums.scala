package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.http.PagingRequest

/**
  * The albums belonging to an artist.
  * https://developer.spotify.com/web-api/get-artists-albums/
  *
  * It's important to note here too that this call has a different response than the albums end point.
  * https://developer.spotify.com/web-api/object-model/#album-object-simplified
  * Why they chose to do this I have no idea, but the return from this will not be the entire album information.
  * @param id
  * @param market
  * @param types
  * @param limit
  * @param offset
  * @param spotify
  */
case class ArtistAlbums(id: String, market: String, types: List[BaseAlbumType] = AlbumTypes.default, limit: Int = 10, offset: Int = 0)(
    implicit spotify: Spotify)
    extends HttpRequest[AlbumPage]
    with PagingRequest[AlbumPage] {

  lazy protected val request: Req = (Endpoints.Artists / id / "albums")
    .addQueryParameter("album_type", types.mkString(","))
    .addQueryParameter("limit", limit.toString)
    .addQueryParameter("offset", offset.toString)
    .addQueryParameter("market", market)
}
