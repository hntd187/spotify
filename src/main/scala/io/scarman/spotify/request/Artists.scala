package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

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

  lazy protected val request: Req = Endpoints.Artists
    .addQueryParameter("ids", ids.mkString(","))
    .addQueryParameter("limit", limit.toString)
    .addQueryParameter("offset", offset.toString)
}
