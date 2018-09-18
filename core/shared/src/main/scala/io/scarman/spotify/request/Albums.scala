package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future

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

  lazy protected val reqUri =
    uri"$base$AB"
      .param("ids", ids.mkString(","))
      .param("market", market)
      .param("limit", limit.toString)
      .param("offset", offset.toString)
}
