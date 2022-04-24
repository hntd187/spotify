package io.scarman.spotify.request

import io.scarman.spotify.*
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** A collection of albums. https://developer.spotify.com/web-api/get-several-albums/
  *
  * @param ids
  * @param market
  * @param limit
  * @param offset
  * @param spotify
  */
case class Albums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0)(implicit
    auth: Authorization,
    backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[response.Albums] {

  lazy protected val reqUri =
    uri"$base$AB"
      .addParam("ids", ids.mkString(","))
      .addParam("market", market)
      .addParam("limit", limit.toString)
      .addParam("offset", offset.toString)
}
