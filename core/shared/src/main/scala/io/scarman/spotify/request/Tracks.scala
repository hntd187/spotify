package io.scarman.spotify.request

import io.scarman.spotify.*
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** Get multiple tracks at once. https://developer.spotify.com/web-api/get-several-tracks/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Tracks(id: List[String], market: String = "ES")(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[response.Tracks] {

  lazy protected val reqUri = uri"$base$TR"
    .addParam("ids", id.mkString(","))
    .addParam("market", market)

}
