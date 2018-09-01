package io.scarman.spotify.request

import com.softwaremill.sttp.SttpBackend
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future
import com.softwaremill.sttp._

/**
  * Get multiple tracks at once.
  * https://developer.spotify.com/web-api/get-several-tracks/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Tracks(id: List[String], market: String = "ES")(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Tracks] {

  lazy protected val reqUri = uri"$base$TR".param("ids", id.mkString(",")).param("market", market)
}
