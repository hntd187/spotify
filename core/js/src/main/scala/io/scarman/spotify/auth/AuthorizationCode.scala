package io.scarman.spotify.auth

import com.softwaremill.sttp.SttpBackend

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js.URIUtils.decodeURIComponent

case class AuthorizationCode(id: String, scope: String, redirectUri: String, showDialog: Boolean = false)(
    implicit backend: SttpBackend[Future, Nothing],
    execution: ExecutionContext = ExecutionContext.Implicits.global) {

  private val params =
    Map("client_id" -> id, "response_type" -> "code", "redirect_uri" -> redirectUri, "scope" -> scope, "show_dialog" -> showDialog)

  private def parseUrl(url: String): Map[String, String] = {
    url
      .split('?')
      .last
      .split('&')
      .map { u =>
        decodeURIComponent(u).split('=') match {
          case Array(k, v) => k -> v
        }
      }
      .toMap
  }

//  def getCreds: ClientCredentials = {
//    val urlParams = parseUrl(window.location.href)
//    urlParams.get("code") match {
//      case Some(c) => ClientCredentials(id, c)
//      case None    => throw new Exception(s"User declined token")
//    }
//  }

  def authApp() = {
//    println(window.location)
//    window.location.href =
//    uri"$authUri/?$params".toString()
    ""
  }
}
