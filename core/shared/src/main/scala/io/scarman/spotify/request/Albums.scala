package io.scarman.spotify.request

import scala.concurrent.Future

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

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
case class Albums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0)(implicit spotify: Spotify,
                                                                                              backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Albums] {
  lazy protected val request = sttp
    .get(uri"$base/$AB".params(("ids", ids.mkString(",")), ("market", market), ("limit", limit.toString), ("offset", offset.toString)))
    .response(asJson[response.Albums])
}
