package io.scarman.spotify.request

import scala.concurrent.Future

import io.circe.Decoder

import io.scarman.spotify.Spotify
import io.scarman.spotify.http._
import monix.execution.Scheduler

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
    implicit spotify: Spotify,
    val scheduler: Scheduler)
    extends HttpRequest[AlbumPage] {

  lazy protected val request = base
    .withPath(s"$AR/$id/albums")
    .withQueryParameter("album_type", types.mkString(","))
    .withQueryParameter("limit", limit.toString)
    .withQueryParameter("offset", offset.toString)
    .withQueryParameter("market", market)

}
