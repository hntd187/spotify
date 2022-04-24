package io.scarman.spotify.http

import io.circe
import io.circe.Decoder
import io.scarman.spotify.*
import io.scarman.spotify.response.*
import scribe.Logging
import sttp.client3.*
import sttp.client3.circe.asJson
import sttp.model.*

import scala.annotation.implicitNotFound
import scala.concurrent.{ExecutionContext, Future}

@implicitNotFound("Cannot find Spotify client, did you create one?")
private[spotify] abstract class HttpRequest[R](implicit
    auth: Authorization,
    d: Decoder[R],
    backend: request.Backend,
    execution: ExecutionContext
) extends Logging
    with MediaTypes {

  protected lazy val reqUri: Uri
  protected val request: Req[R] = basicRequest.get(reqUri).response(asJson[R])

  def apply(): Future[R] = {
    for (response <- get(request))
      yield response
  }

  private def toJson(resp: NoFResp[R]): Either[ErrorCase, R] = {
    resp.body match {
      case Right(v) if resp.is200               => Right(v)
      case Left(DeserializationException(_, e)) => Left(ErrorCase(Error(resp.code.code, e.getMessage)))
      case Left(HttpError(body, statusCode))    => Left(ErrorCase(Error(statusCode.code, body)))
      case e                                    => throw new RuntimeException(s"An unknown exception occurred: ${e.toString}")
    }
  }

  protected def get(req: Req[R]): Future[R] = {
    logger.debug(s"Request made for URL: ${req.uri}")
    auth.getToken.flatMap { t =>
      val authReq = req.auth.bearer(t.access_token)
      val resp    = authReq.send(backend)
      resp.map { r =>
        toJson(r) match {
          case Right(v) => v
          case Left(e)  => throw new Exception(s"${e.toString} - ${authReq.uri}\n")
        }
      }
    }
  }
}
