package io.scarman.spotify.request

import io.circe.Decoder
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.model.Uri

import scala.concurrent.ExecutionContext

case class RequestPage[R](r: Uri, pageNumber: Int = 1)(implicit
    auth: Authorization,
    val backend: Backend,
    d: Decoder[R],
    ec: ExecutionContext
) extends HttpRequest[R] {

  override lazy val reqUri: Uri = r
}
