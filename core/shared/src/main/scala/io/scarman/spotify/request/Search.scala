package io.scarman.spotify
package request

import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*
import sttp.model.Uri

import scala.concurrent.ExecutionContext

/** It's best to view the documentation here to know what queries are valid for searching.
  * https://developer.spotify.com/documentation/web-api/reference-beta/#category-search
  * @param q:
  *   Actual search query
  * @param tpe:
  *   category of search
  * @param market:
  *   market for search to be in
  * @param limit:
  *   number of returned docs
  * @param offset:
  *   where in paging to start
  * @param include_external:
  *   what external content to include or not
  * @param auth:
  *   implicit API keys
  * @param backend:
  *   implicit HTTP Client
  */
case class Search(q: String, tpe: String, market: String = "US", limit: Int = 20, offset: Int = 0, include_external: Option[String] = None)(
    implicit
    auth: Authorization,
    backend: Backend,
    ec: ExecutionContext
) extends HttpRequest[response.SearchResults] {

  lazy val reqUri: Uri = uri"${base}search"
    .addParam("market", market)
    .addParam("q", q)
    .addParam("type", tpe)
    .addParam("limit", limit.toString)
    .addParam("offset", offset.toString)

}
