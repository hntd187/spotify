package io.scarman.spotify.request

import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client._

/**
  * Get more than one artist at once.
  * https://developer.spotify.com/web-api/get-several-artists/
  *
  * @param ids
  * @param limit
  * @param offset
  * @param spotify
  */
case class Artists(ids: List[String], limit: Int = 10, offset: Int = 5)(implicit auth: Authorization, backend: Backend)
    extends HttpRequest[response.Artists] {

  lazy protected val reqUri = uri"$base$AR/?ids=$ids"
    .param("limit", limit.toString())
    .param("offset", offset.toString())
}
