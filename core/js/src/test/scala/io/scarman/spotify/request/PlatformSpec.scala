package io.scarman.spotify.request

import sttp.client.FetchBackend

import scala.concurrent.ExecutionContext

object PlatformSpec {
  implicit def executionContext: ExecutionContext = ExecutionContext.Implicits.global
  implicit val backend: Backend                   = FetchBackend()
}
