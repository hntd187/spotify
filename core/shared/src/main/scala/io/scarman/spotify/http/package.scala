package io.scarman.spotify

import com.softwaremill.sttp._
import io.circe.Error

import scala.concurrent.Future

package object http {
  type Req[R]     = RequestT[Id, Either[DeserializationError[Error], R], Nothing]
  type Resp[R]    = Future[Response[Either[DeserializationError[Error], R]]]
  type NoFResp[R] = Response[Either[DeserializationError[Error], R]]
}
