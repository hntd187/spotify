package io.scarman.spotify.auth

import java.util.concurrent.atomic.AtomicReference

import com.softwaremill.sttp.circe.asJson
import com.softwaremill.sttp._
import io.scarman.spotify.http._
import io.scarman.spotify.request._
import io.scarman.spotify.response.AccessToken
import scribe._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Left, Right}

case class ClientCredentials(appId: String, appSecret: String)(implicit backend: SttpBackend[Future, Nothing],
                                                               execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends Authorization {

  private val baseBody                                            = ("grant_type", "client_credentials")
  lazy private val tokenRef: AtomicReference[Future[AccessToken]] = new AtomicReference[Future[AccessToken]](initToken(appId, appSecret))

  private val baseRequest: Req[AccessToken] = sttp
    .post(tokenUri)
    .contentType(MediaTypes.Form)
    .response(asJson[AccessToken])
    .header("Access-Control-Allow-Origin", "*")
    .body(baseBody)

  private def tokenRequest(req: Req[AccessToken]): Future[AccessToken] = {
    req.send().map(_.body).map {
      case Right(Right(at)) =>
        info(s"Auth Token: ${at.access_token}")
        at
      case Right(Left(err)) => throw new Exception(s"Can't get auth token: $req\n$err")
      case Left(err)        => throw new Exception(s"Can't get auth token: $req\n$err")
    }
  }

  protected def initToken(id: String, secret: String): Future[AccessToken] = {
    info(s"$id - $secret")
    val request = baseRequest.auth.basic(id, secret)
    debug(s"Headers: ${request.headers.map { case (k, v) => s"$k=$v" }.mkString(", ")}")
    tokenRequest(request)
  }

  def refreshToken(): Future[AccessToken] = {
    tokenRef.get().flatMap { t =>
      if (!t.isExpired) {
        Future.successful(t)
      } else {
        tokenRef.getAndSet(getToken)
      }
    }
  }

  def getToken: Future[AccessToken] = {
    tokenRef.get().flatMap { t =>
      if (t.isExpired) {
        debug("Token expired, requesting new one...")
        tokenRef.getAndSet(refreshToken())
      } else {
        debug(s"Providing Token: ${t.access_token}")
        tokenRef.get()
      }
    }
  }

  def isExpired: Future[Boolean] = {
    tokenRef.get().map(_.isExpired)
  }
}
