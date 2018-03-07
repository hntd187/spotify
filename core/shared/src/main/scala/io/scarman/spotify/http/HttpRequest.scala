package io.scarman.spotify.http

import scala.annotation.implicitNotFound
import scala.concurrent.{ExecutionContext, Future}

import fr.hmil.roshttp.response.SimpleHttpResponse
import fr.hmil.roshttp.{HttpRequest => HR}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import scribe.Logging
import io.scarman.spotify._
import io.scarman.spotify.response._
import monix.execution.Scheduler

@implicitNotFound("Cannot find Spotify client, did you create one?")
private[spotify] abstract class HttpRequest[R](implicit spotify: Spotify, d: Decoder[R], scheduler: Scheduler)
    extends RequestJson
    with LastResponse[R]
    with Logging {

  implicit private val ec: ExecutionContext = scheduler
  protected val request: HR

  def apply(): Future[R] = {
    for (response <- get(request))
      yield response
  }

  private def toJson(resp: SimpleHttpResponse): Either[ErrorCase, R] = {
    val body = resp.body
    json = parse(body).toOption
    resp.statusCode match {
      case 200 =>
        lastResponse = json.get.as[R]
        lastResponse match {
          case Right(v) => Right(v)
          case Left(e)  => Left(ErrorCase(response.Error(200, e.getMessage)))
        }
      case _ =>
        json match {
          case Some(j) =>
            j.as[ErrorCase] match {
              case Right(ec) => Left(ec)
              case Left(df)  => throw df
            }
          case None => Left(ErrorCase(response.Error(resp.statusCode, "Empty, non 200 response returned")))
        }
    }
  }

  protected def get(req: HR): Future[R] = {
    logger.info(s"Request made for URL: ${req.url}")
    spotify.getToken.flatMap { t =>
      val authReq = req.withHeaders(("Authorization", s"Bearer $t"), ("Content-Type", "application/json"))
      for {
        resp <- authReq.send()
        data = toJson(resp)
      } yield {
        data match {
          case Right(r) => r
          case Left(ErrorCase(response.Error(code, message))) =>
            logger.info(resp.body)
            throw new RuntimeException(s"Error, Code: $code, Message: $message")
        }
      }
    }
  }
}
