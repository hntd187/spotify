package io.scarman.spotify.request

import scala.concurrent.Future

import com.softwaremill.sttp.{SttpBackend, _}
import com.softwaremill.sttp.circe.asJson
import io.scarman.spotify._
import io.scarman.spotify.http.{HttpRequest, Req}

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
  lazy protected val request: Req[response.Album] = sttp.get(uri"$base/$AB/$id".param("market", market)).response(asJson[response.Album])

  def tracks(limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)
  }
}
