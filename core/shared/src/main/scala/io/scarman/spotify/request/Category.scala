package io.scarman.spotify.request

import com.softwaremill.sttp.{SttpBackend, UriContext, Uri}
import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response.{Category => CategoryResp}

import scala.concurrent.Future

case class Category(categoryId: String, country: Option[String] = None, locale: Option[String] = None)(
    implicit auth: Authorization,
    backend: SttpBackend[Future, Nothing]
) extends HttpRequest[CategoryResp] {

  lazy protected val reqUri: Uri =
    uri"$base/browse/categories/$categoryId?country=$country&locale=$locale"
}
