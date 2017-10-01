package io.scarman.spotify.http

import org.json4s.JValue

private[spotify] trait RequestJson {
  private[http] var json: Option[JValue] = None
}
