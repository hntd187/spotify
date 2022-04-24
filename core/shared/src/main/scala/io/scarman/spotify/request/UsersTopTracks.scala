package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.request.TimeRange.MediumTerm
import io.scarman.spotify.response
import io.scarman.spotify.response.Paging
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** @see
  *   https://developer.spotify.com/documentation/web-api/reference/personalization/get
  * -users-top-artists-and-tracks/
  *
  * @note
  *   Requires auth for a user with the scope `user-top-read`, which currently means only using an instance of {@link AuthorizationCode}
  * @param limit
  *   The number of entities to return. Default: 20. Minimum: 1. Maximum: 50. For example: limit=2
  * @param offset
  *   The index of the first entity to return. Default: 0 (i.e., the first track). Use with limit to get the next set of entities.
  * @param timeRange
  *   Over what time frame the affinities are computed.
  */
case class UsersTopTracks(limit: Int = 20, offset: Int = 0, timeRange: TimeRange = MediumTerm)(implicit
    auth: Authorization,
    backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[Paging[response.Track]] {

  lazy protected val reqUri = uri"$base/me/top/tracks"
    .addParam("limit", limit.toString)
    .addParam("offset", offset.toString)
    .addParam("time_range", timeRange.name)
}
