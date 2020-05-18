package io.scarman.spotify.auth

import java.util.concurrent.atomic.AtomicReference

import sttp.client._
import sttp.model._
import io.circe.syntax._
import io.scarman.spotify.http.Authorization
import io.scarman.spotify.request._
import io.scarman.spotify.response.AccessToken
import org.scalajs.dom.window

import scala.concurrent.{ExecutionContext, Future}

case class AuthorizationCode(id: String, scope: String, redirectUri: String, showDialog: Boolean = false) {

  implicit def uriAsString(uri: Uri): String = {
    uri.toString()
  }

  private val params = Map(
    "client_id"     -> id,
    "response_type" -> "token",
    "redirect_uri"  -> redirectUri,
    "scope"         -> scope,
    "show_dialog"   -> showDialog
  )

  def getAccessToken: String = {
    val urlParams = AuthorizationCode.parseUrl(window.location.href)
    urlParams.get("code") match {
      case Some(c) => c
      case None    => throw new Exception(s"User declined token")
    }
  }

  def authUrl(): String = {
    uri"$authUri/?$params"
  }
}

object AuthorizationCode {
  def parseUrl(url: String): Map[String, String] = {
    // Access token is a hash fragment
    val split = url.split('#')
    if (split.length <= 1)
      Map.empty
    else {
      split.lastOption
        .map(_.split('&').map { u =>
          u.split('=') match {
            case Array(k, v) => k -> v
          }
        }.toMap)
        .getOrElse(Map.empty)
    }
  }
}

case class ImplicitCredentials()(implicit ec: ExecutionContext) extends Authorization {

  // Implicit doesn't have app, secret keys
  lazy private val tokenRef: AtomicReference[Future[AccessToken]] = new AtomicReference[Future[AccessToken]](initToken("", ""))

  protected def initToken(id: String, secret: String): Future[AccessToken] = {
    val params = AuthorizationCode.parseUrl(window.location.href).asJson
    val cursor = params.hcursor

    Future {
      val code      = cursor.downField("access_token").as[String].getOrElse(throw new Exception())
      val expiresIn = cursor.downField("expires_in").as[Int].getOrElse(3600)
      val scope     = cursor.downField("scope").as[String].toOption

      scribe.info(s"Code: $code")
      scribe.info(s"Exp: $expiresIn")
      scribe.info(s"Scope: $scope")
      AccessToken(code, "Bearer", expiresIn, scope, None, None)
    }
  }

  def getToken: Future[AccessToken]       = tokenRef.get()
  def refreshToken(): Future[AccessToken] = throw new UnsupportedOperationException("Implicit Grants do not support refresh")
  def isExpired: Future[Boolean]          = tokenRef.get().map(_.isExpired)
}
