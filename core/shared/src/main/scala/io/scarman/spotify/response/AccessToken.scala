package io.scarman.spotify.response

case class AccessToken(access_token: String, token_type: String, expires_in: Int, scope: Option[String], refresh_token: Option[String]) {

  private val expiresOn  = (System.currentTimeMillis() / 1000) + expires_in
  def isExpired: Boolean = System.currentTimeMillis() / 1000 > expiresOn

}

object AccessToken {
  def expired: AccessToken = AccessToken("", "", -1, None, None)
}
