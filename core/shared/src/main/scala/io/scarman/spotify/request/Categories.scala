package io.scarman.spotify.request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import io.scarman.spotify.response.{Categories => CategoriesResp}
import sttp.client._
import sttp.model._

case class Categories(country: Option[String] = None, locale: Option[String] = None, limit: Int = 20, offset: Int = 0)(
    implicit auth: Authorization,
    backend: Backend
) extends HttpRequest[CategoriesResp] {

  lazy val parameters: Map[String, Any] = Map(
    "country" -> country,
    "locale"  -> locale,
    "limit"   -> limit,
    "offset"  -> offset
  )
  protected lazy val reqUri: Uri = uri"$base/browse/categories?$parameters"
}
