package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

/**
  * Get multiple tracks at once.
  * https://developer.spotify.com/web-api/get-several-tracks/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Tracks(id: List[String], market: String = "ES")(implicit auth: Authorization, backend: Backend)
    extends HttpRequest[response.Tracks] {

  lazy protected val reqUri = uri"$base$TR"
    .param("ids", id.mkString(","))
    .param("market", market)

}
