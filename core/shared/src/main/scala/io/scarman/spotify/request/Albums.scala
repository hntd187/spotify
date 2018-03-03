package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._

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
  lazy protected val request = base
    .withPath(s"$AB/")
    .withQueryParameter("ids", ids.mkString(","))
    .withQueryParameter("market", market)
    .withQueryParameter("limit", limit.toString)
    .withQueryParameter("offset", offset.toString)
}
