package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._

/**
  * Get multiple tracks at once.
  * https://developer.spotify.com/web-api/get-several-tracks/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Tracks(id: List[String], market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[response.Tracks] {
  override protected val request = base
    .withPath(TR)
    .withQueryParameter("ids", id.mkString(","))
    .withQueryParameter("market", market)
}
