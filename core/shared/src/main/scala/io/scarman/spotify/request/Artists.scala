package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future

/**
  * Get more than one artist at once.
  * https://developer.spotify.com/web-api/get-several-artists/
  *
  * @param ids
  * @param limit
  * @param offset
  * @param spotify
  */
case class Artists(ids: List[String], limit: Int = 10, offset: Int = 5)(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/?ids=$ids"
    .param("limit", limit.toString)
    .param("offset", offset.toString)
}
