package io.scarman.spotify

import io.circe.Error
import sttp.client._

import scala.concurrent.Future

package object http {
  type Req[R]     = RequestT[Identity, Either[ResponseError[Error], R], Nothing]
  type StrReq     = RequestT[Identity, String, Nothing]
  type Resp[R]    = Future[Response[Either[DeserializationError[Error], R]]]
  type NoFResp[R] = Response[Either[ResponseError[Error], R]]
}
