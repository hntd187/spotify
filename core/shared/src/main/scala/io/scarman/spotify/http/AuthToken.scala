package io.scarman.spotify.http

import java.util.Base64

import scala.concurrent._
import scala.util._

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.circe.generic.auto._
import io.scarman.spotify.request.Endpoints
import io.scarman.spotify.response.AccessToken
import scribe.Logging

private[spotify] trait AuthToken extends Logging with MediaTypes with HeaderNames {

  implicit val backend: SttpBackend[Future, Nothing]
  implicit val execution: ExecutionContext

  private val baseBody = ("grant_type", "client_credentials")

  private val baseRequest: Req[AccessToken] = sttp
    .post(Endpoints.Token)
    .header(ContentType, Form)
    .header(AccessControlAllowOrigin, "*")
    .response(asJson[AccessToken])
    .body(baseBody)

  private def base64(id: String, secret: String): String = {
    Base64.getEncoder.encodeToString(s"$id:$secret".getBytes)
  }

  private def tokenRequest(req: Req[AccessToken]): Future[AccessToken] = {
    req.send().map(_.body).map {
      case Right(Right(at)) => at
      case Right(Left(err)) => throw new Exception(s"Can't get auth token $req\n$err")
      case Left(err)        => throw new Exception(s"Can't get auth token: $req\n$err")
    }
  }

  protected def getToken(id: String, secret: String): Future[AccessToken] = {
    val authHeader: String = base64(id, secret)
    val request: Req[AccessToken] = baseRequest
      .header(Authorization, s"Basic $authHeader")

    logger.info(s"Requesting token for $authHeader")
    tokenRequest(request)

  }

  protected[spotify] def refreshToken(id: String, secret: String, token: Future[AccessToken]): Future[AccessToken] = {
    token.flatMap { t =>
      if (!t.isExpired) {
        Future.successful(t)
      } else {
        getToken(id, secret)
      }
    }
  }
}
