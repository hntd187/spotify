package io.scarman.spotify.http

import java.util.Base64
import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._
import dispatch.as.json4s._
import org.json4s.{DefaultFormats, JValue}
import org.slf4j.{Logger, LoggerFactory}

import io.scarman.spotify.request.Endpoints
import io.scarman.spotify.response.AccessToken

private[spotify] trait AuthToken {

  protected val logger: Logger                 = LoggerFactory.getLogger(getClass)
  private implicit val formats: DefaultFormats = DefaultFormats

  private val baseRequest: Req = Endpoints.Token
    .addHeader("Content-Type", "application/x-www-form-urlencoded")

  private val baseBody: String = "grant_type=client_credentials"

  private def base64(id: String, secret: String): String = {
    Base64.getEncoder.encodeToString(s"$id:$secret".getBytes)
  }

  private def tokenRequest(req: Req): JValue = {
    val token: Future[JValue] = Http.default(req.POST OK Json)
    token()
  }

  protected def getToken(id: String, secret: String): AccessToken = {
    val authHeader: String = base64(id, secret)
    val request: Req = baseRequest
      .addHeader("Authorization", s"Basic $authHeader")
      .setBody(baseBody)
    logger.info(s"Requesting token for $authHeader")
    tokenRequest(request).extract[AccessToken]
  }

  protected[spotify] def refreshToken(id: String, secret: String, token: AccessToken): AccessToken = {
    if (!token.isExpired) token
    else {
      getToken(id, secret)
    }
  }
}
