package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response.Category as CategoryResp
import sttp.client3.*
import sttp.model.Uri

import scala.concurrent.ExecutionContext

case class Category(categoryId: String, country: Option[String] = None, locale: Option[String] = None)(implicit
    auth: Authorization,
    backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[CategoryResp] {

  lazy protected val reqUri: Uri =
    uri"$base/browse/categories/$categoryId?country=$country&locale=$locale"
}
