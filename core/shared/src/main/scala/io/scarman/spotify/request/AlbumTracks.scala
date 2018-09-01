package io.scarman.spotify.request

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.scarman.spotify.Spotify
import io.scarman.spotify.http._

import scala.concurrent.Future

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
case class AlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 5)(implicit spotify: Spotify,
                                                                                            val backend: SttpBackend[Future, Nothing])
    extends HttpRequest[TrackPage] {

  lazy protected val reqUri = uri"$base$AB/$id/tracks"
    .param("market", market)
    .param("limit", limit.toString)
    .param("offset", offset.toString)

  def nextPage(): Option[AlbumTracks] = {
    for {
      p <- this()().next
    } yield AlbumTracks(p)
  }
}
