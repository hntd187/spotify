package io.scarman.spotify.http

import java.util.Base64

import com.softwaremill.sttp._
import com.softwaremill.sttp.circe._
import io.circe.generic.auto._
import io.scarman.spotify.request._
import io.scarman.spotify.response.AccessToken
import scribe.Logging

import scala.concurrent._
import scala.util._

abstract class AuthToken(implicit backend: SttpBackend[Future, Nothing], execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends Logging
    with MediaTypes
    with HeaderNames {

  private val baseBody = ("grant_type", "client_credentials")

  private val baseRequest: Req[AccessToken] = sttp
    .post(Token)
    .header(ContentType, Form)
    .response(asJson[AccessToken])
    .body(baseBody)

  private def base64(id: String, secret: String): String = {
    Base64.getEncoder.encodeToString(s"$id:$secret".getBytes)
  }

  private def tokenRequest(req: Req[AccessToken]): Future[AccessToken] = {
    req.send().map(_.body).map {
      case Right(Right(at)) => at
      case Right(Left(err)) => throw new Exception(s"Can't get auth token: $req\n$err")
      case Left(err)        => throw new Exception(s"Can't get auth token: $req\n$err")
    }
  }

  protected def getToken(id: String, secret: String, previous: Option[AccessToken] = None): Future[AccessToken] = {
    val authHeader: String        = base64(id, secret)
    val request: Req[AccessToken] = baseRequest.header(Authorization, s"Basic $authHeader")
    logger.debug(s"Requesting token for $authHeader")
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
