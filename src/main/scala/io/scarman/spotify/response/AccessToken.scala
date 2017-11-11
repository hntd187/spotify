package io.scarman.spotify.response

import java.time.LocalDateTime

case class AccessToken(access_token: String, token_type: String, expires_in: Int, scope: Option[String], refresh_token: Option[String]) {

  val expiresOn          = LocalDateTime.now().plusSeconds(expires_in)
  def isExpired: Boolean = LocalDateTime.now().isAfter(expiresOn)
}
