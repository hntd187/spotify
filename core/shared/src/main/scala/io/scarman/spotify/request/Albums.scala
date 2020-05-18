package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

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
case class Albums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0)(implicit auth: Authorization,
                                                                                              backend: Backend)
    extends HttpRequest[response.Albums] {

  lazy protected val reqUri =
    uri"$base$AB"
      .param("ids", ids.mkString(","))
      .param("market", market)
      .param("limit", limit.toString())
      .param("offset", offset.toString())
}
