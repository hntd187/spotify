package io.scarman.spotify.http

import io.circe.Json

private[spotify] trait RequestJson {
  private[http] var json: Option[Json] = None
}
