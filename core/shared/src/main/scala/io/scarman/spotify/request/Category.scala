package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response.{Category => CategoryResp}
import sttp.client._
import sttp.model.Uri

case class Category(categoryId: String, country: Option[String] = None, locale: Option[String] = None)(
    implicit auth: Authorization,
    backend: Backend
) extends HttpRequest[CategoryResp] {

  lazy protected val reqUri: Uri =
    uri"$base/browse/categories/$categoryId?country=$country&locale=$locale"
}
