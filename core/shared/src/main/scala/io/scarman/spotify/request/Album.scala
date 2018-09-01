package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future

/**
  * An album of fire.
  * https://developer.spotify.com/web-api/get-album/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Album(id: String, market: String = "ES")(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Album] {

  lazy protected val reqUri = uri"$base$AB/$id".param("market", market)

  def tracks(limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)
  }
}
