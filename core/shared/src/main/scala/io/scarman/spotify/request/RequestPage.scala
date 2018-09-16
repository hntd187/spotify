package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.circe.Decoder
import io.scarman.spotify.Spotify
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future

case class RequestPage[R](override val reqUri: Uri, pageNumber: Int = 1)(implicit spotify: Spotify,
                                                                         val backend: SttpBackend[Future, Nothing],
                                                                         d: Decoder[R])
    extends HttpRequest[R]
