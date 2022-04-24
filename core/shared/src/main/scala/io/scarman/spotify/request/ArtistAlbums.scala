package io.scarman.spotify.request

import io.scarman.spotify.http.*
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** The albums belonging to an artist. https://developer.spotify.com/web-api/get-artists-albums/
  *
  * It's important to note here too that this call has a different response than the albums end point.
  * https://developer.spotify.com/web-api/object-model/#album-object-simplified Why they chose to do this I have no idea, but the return
  * from this will not be the entire album information.
  *
  * @param id
  * @param market
  * @param types
  * @param limit
  * @param offset
  * @param spotify
  */
case class ArtistAlbums(id: String, market: String, types: List[AlbumType] = AlbumType.default, limit: Int = 10, offset: Int = 0)(implicit
    auth: Authorization,
    val backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[AlbumPage] {

  lazy protected val reqUri =
    uri"$base$AR/$id/albums"
      .addParam("album_type", types.mkString(","))
      .addParam("limit", limit.toString)
      .addParam("offset", offset.toString)
      .addParam("market", market)

}
