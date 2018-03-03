package io.scarman.spotify.request

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify._

/**
  * Get more than one artist at once.
  * https://developer.spotify.com/web-api/get-several-artists/
  *
  * @param ids
  * @param limit
  * @param offset
  * @param spotify
  */
case class Artists(ids: List[String], limit: Int = 10, offset: Int = 5)(implicit spotify: Spotify) extends HttpRequest[response.Artists] {

  lazy protected val request = base
    .withPath(s"$AR/")
    .withQueryStringRaw(s"ids=${ids.mkString(",")}")
    .withQueryParameter("limit", limit.toString)
    .withQueryParameter("offset", offset.toString)
}
