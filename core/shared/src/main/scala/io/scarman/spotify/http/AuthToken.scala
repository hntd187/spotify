package io.scarman.spotify.http

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
    .contentType(Form)
    .response(asJson[AccessToken])
    .body(baseBody)

  private def tokenRequest(req: Req[AccessToken]): Future[AccessToken] = {
    req.send().map(_.body).map {
      case Right(Right(at)) =>
        logger.info(s"Auth Token: ${at.access_token}")
        at
      case Right(Left(err)) => throw new Exception(s"Can't get auth token: $req\n$err")
      case Left(err)        => throw new Exception(s"Can't get auth token: $req\n$err")
    }
  }

  protected def getToken(id: String, secret: String, previous: Option[AccessToken] = None): Future[AccessToken] = {
    val request: Req[AccessToken] = baseRequest.auth.basic(id, secret)
    logger.debug(s"Headers: ${request.headers.map { case (k, v) => s"$k=$v" }.mkString(", ")}")
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
