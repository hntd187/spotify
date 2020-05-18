package io.scarman.spotify.request

import io.scarman.spotify.http._
import sttp.client._

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
case class AlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 5)(implicit auth: Authorization,
                                                                                            val backend: Backend)
    extends HttpRequest[TrackPage] {

  lazy protected val reqUri = uri"$base$AB/$id/tracks"
    .param("market", market)
    .param("limit", limit.toString())
    .param("offset", offset.toString())

}
