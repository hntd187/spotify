package io.scarman.spotify.http

import org.json4s.JValue
import org.json4s.jackson.JsonMethods._

private[spotify] trait RequestJson {
  private[http] var json: Option[JValue]                = None
  protected[spotify] def responseJson(): Option[String] = json.map(pretty)
}
