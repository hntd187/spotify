package io.scarman.spotify.http

import io.circe.Error
import sttp.client3.*

import scala.concurrent.Future

type Req[R]     = RequestT[Identity, Either[ResponseException[String, Error], R], Any]
type StrReq     = RequestT[Identity, String, Any]
type Resp[R]    = Future[Response[Either[HttpError[Error], R]]]
type NoFResp[R] = Response[Either[ResponseException[String, Error], R]]
