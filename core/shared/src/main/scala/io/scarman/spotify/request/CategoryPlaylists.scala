package io.scarman.spotify.request

import com.softwaremill.sttp.{SttpBackend, UriContext}
import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response.Playlists

import scala.concurrent.Future

case class CategoryPlaylists(categoryId: String, country: Option[String] = None, limit: Int = 20, offset: Int = 0)(
    implicit auth: Authorization,
    val backend: SttpBackend[Future, Nothing]
) extends HttpRequest[Playlists] {

  lazy val parameters: Map[String, Any] = Map(
    "country" -> country,
    "limit"   -> limit,
    "offset"  -> offset
  )

  lazy protected val reqUri =
    uri"$base/browse/categories/$categoryId/playlists?$parameters"
}
