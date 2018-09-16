package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify.Spotify
import io.scarman.spotify.http._

import scala.concurrent.Future

/**
  * The albums belonging to an artist.
  * https://developer.spotify.com/web-api/get-artists-albums/
  *
  * It's important to note here too that this call has a different response than the albums end point.
  * https://developer.spotify.com/web-api/object-model/#album-object-simplified
  * Why they chose to do this I have no idea, but the return from this will not be the entire album information.
  *
  * @param id
  * @param market
  * @param types
  * @param limit
  * @param offset
  * @param spotify
  */
case class ArtistAlbums(id: String, market: String, types: List[BaseAlbumType] = AlbumTypes.default, limit: Int = 10, offset: Int = 0)(
    implicit spotify: Spotify,
    val backend: SttpBackend[Future, Nothing])
    extends HttpRequest[AlbumPage] {

  lazy protected val reqUri =
    uri"$base$AR/$id/albums"
      .param("album_type", types.mkString(","))
      .param("limit", limit.toString)
      .param("offset", offset.toString)
      .param("market", market)

}
