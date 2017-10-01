package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.{Spotify, response}

/**
  * An album of fire.
  * https://developer.spotify.com/web-api/get-album/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Album(id: String, market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[response.Album] {
  lazy protected val request: Req = (Endpoints.Albums / id).addQueryParameter("market", market)

  def tracks(limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)
  }
}
