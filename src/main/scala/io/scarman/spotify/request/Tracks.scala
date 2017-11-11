package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

/**
  * Get multiple tracks at once.
  * https://developer.spotify.com/web-api/get-several-tracks/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Tracks(id: List[String], market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[response.Tracks] {
  override protected val request: Req = Endpoints.Tracks
    .addQueryParameter("ids", id.mkString(","))
    .addQueryParameter("market", market)
}
