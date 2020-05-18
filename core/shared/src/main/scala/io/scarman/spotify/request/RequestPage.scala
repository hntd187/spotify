package io.scarman.spotify.request

import io.circe.Decoder
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.model.Uri

case class RequestPage[R](override val reqUri: Uri, pageNumber: Int = 1)(implicit auth: Authorization, val backend: Backend, d: Decoder[R])
    extends HttpRequest[R]
