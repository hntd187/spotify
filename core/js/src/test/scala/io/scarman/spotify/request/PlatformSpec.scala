package io.scarman.spotify.request

import com.softwaremill.sttp._

import scala.concurrent.ExecutionContext

object PlatformSpec {
  implicit val executionContext = ExecutionContext.Implicits.global
  implicit val backend          = FetchBackend()
}
