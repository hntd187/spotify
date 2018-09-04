package io.scarman.spotify.http

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.circe.Decoder
import io.scarman.spotify._
import io.scarman.spotify.response._
import scribe.Logging

import scala.annotation.implicitNotFound
import scala.concurrent.{ExecutionContext, Future}

@implicitNotFound("Cannot find Spotify client, did you create one?")
private[spotify] abstract class HttpRequest[R](implicit spotify: Spotify,
                                               d: Decoder[R],
                                               backend: SttpBackend[Future, Nothing],
                                               val execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends Logging {

  protected val reqUri: Uri
  protected val request: Req[R] = sttp.get(reqUri).response(asJson[R])

  def apply(): Future[R] = {
    for (response <- get(request))
      yield response
  }

  private def toJson(resp: NoFResp[R]): Either[ErrorCase, R] = {
    if (resp.is200) {
      resp.unsafeBody match {
        case Right(v) => Right(v)
        case Left(e)  => Left(ErrorCase(response.Error(resp.code, e.message)))
      }
    } else {
      Left(ErrorCase(response.Error(resp.code, s"Non-200 Response code ${resp.code}, ${resp.statusText}")))
    }
  }

  protected def get(req: Req[R]): Future[R] = {
    logger.debug(s"Request made for URL: ${req.uri}")
    spotify.getToken.flatMap { t =>
      val authReq = req.headers(("Authorization", s"Bearer $t"), ("Content-Type", "application/json"))
      val resp    = authReq.send()
      resp.map { r =>
        toJson(r) match {
          case Right(v) => v
          case Left(e)  => throw new Exception(s"${e.error.message} - ${authReq.uri}")
        }
      }
    }
  }
}
