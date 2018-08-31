package io.scarman.spotify.http

import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.softwaremill.sttp._
import io.circe.{Decoder, Error}
import io.scarman.spotify._
import io.scarman.spotify.response._
import scribe.Logging

@implicitNotFound("Cannot find Spotify client, did you create one?")
private[spotify] abstract class HttpRequest[R](implicit spotify: Spotify, d: Decoder[R], backend: SttpBackend[Future, Nothing])
    extends RequestJson
    with LastResponse[R]
    with Logging {

  protected val request: Req[R]

  def apply(): Future[R] = {
    for (response <- get(request))
      yield response
  }

  private def toJson(resp: NoFResp[R]): Either[ErrorCase, R] = {
    if (resp.is200) {
      resp.body match {
        case Left(e)         => Left(ErrorCase(Error(resp.code, e)))
        case Right(Right(v)) => Right(v)
      }
    } else {
      Left(ErrorCase(Error(resp.code, s"Non-200 Response code ${resp.code}")))
    }
  }

  protected def get(req: Req[R]): Future[R] = {
    logger.debug(s"Request made for URL: ${req.uri}")
    spotify.getToken.flatMap { t =>
      val authReq = req.headers(("Authorization", s"Bearer $t"), ("Content-Type", "application/json"))
      val resp    = authReq.send()
      resp.map{r => toJson(r) match {
        case Right(v) => v
        case Left(e)  => throw new Exception(e.error.message)
      }}

    }
  }
}
