package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.circe.Decoder
import io.scarman.spotify.http.{Authorization, HttpRequest}

import scala.concurrent.Future

case class RequestPage[R](override val reqUri: Uri, pageNumber: Int = 1)(implicit auth: Authorization,
                                                                         val backend: SttpBackend[Future, Nothing],
                                                                         d: Decoder[R])
    extends HttpRequest[R]
