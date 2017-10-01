package io.scarman.spotify.http

import scala.annotation.implicitNotFound
import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._
import org.asynchttpclient.Response
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, Formats, StringInput}
import org.slf4j.{Logger, LoggerFactory}

import io.scarman.spotify.Spotify
import io.scarman.spotify.response.{Error, ErrorCase}

@implicitNotFound("Cannot find Spotify client, did you create one?")
private[spotify] abstract class HttpRequest[R](implicit mf: Manifest[R], spotify: Spotify) extends RequestJson with LastResponse[R] {

  private implicit val formats: Formats = DefaultFormats
  protected val logger: Logger          = LoggerFactory.getLogger(getClass)
  protected val request: Req

  def apply(): Future[R] = {
    for (response <- get(request))
      yield response
  }

  private def toJson(resp: Response): Either[ErrorCase, R] = {
    val body: String = resp.getResponseBody
    json = Some(parse(StringInput(body), useBigDecimalForDouble = true))
    resp.getStatusCode match {
      case 200 =>
        lastResponse = Some(json.get.extract[R])
        Right(lastResponse.get)
      case _ => Left(json.get.extract[ErrorCase])
    }
  }

  protected def get(req: Req): Future[R] = {
    logger.info(s"Request made for URL: ${req.url}")
    val authReq: Req = req.addHeader("Authorization", s"Bearer ${spotify.getToken}")
    for {
      resp <- Http.default(authReq)
      data = toJson(resp)
    } yield {
      data match {
        case Right(d) => d
        case Left(ErrorCase(Error(code, message))) =>
          throw new RuntimeException(s"Error, Code: $code, Message: $message")
      }
    }
  }
}
