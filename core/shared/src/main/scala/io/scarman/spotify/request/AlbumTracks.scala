package io.scarman.spotify.request

import io.scarman.spotify.http.*
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** The tracks on an album. https://developer.spotify.com/web-api/get-albums-tracks/
  *
  * @param id
  * @param market
  * @param limit
  * @param offset
  * @param spotify
  */
case class AlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 5)(implicit
    auth: Authorization,
    val backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[TrackPage] {

  lazy protected val reqUri =
    uri"$base$AB/$id/tracks".addParam("market", market).addParam("limit", limit.toString).addParam("offset", offset.toString)

}
