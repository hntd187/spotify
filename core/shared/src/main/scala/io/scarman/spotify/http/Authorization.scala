package io.scarman.spotify.http

import io.scarman.spotify.response.AccessToken

import scala.concurrent.Future

trait Authorization {

  def initToken(id: String, secret: String): Future[AccessToken]

  def getToken: Future[AccessToken]

  def refreshToken(): Future[AccessToken]

  def isExpired: Future[Boolean]

}
