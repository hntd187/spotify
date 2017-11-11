package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

/**
  * A collection of albums.
  * https://developer.spotify.com/web-api/get-several-albums/
  *
  * @param ids
  * @param market
  * @param limit
  * @param offset
  * @param spotify
  */
case class Albums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0)(implicit spotify: Spotify)
    extends HttpRequest[response.Albums] {
  lazy protected val request: Req = Endpoints.Albums
    .addQueryParameter("ids", ids.mkString(","))
    .addQueryParameter("market", market)
    .addQueryParameter("limit", limit.toString)
    .addQueryParameter("offset", offset.toString)
}
