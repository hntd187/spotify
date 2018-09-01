package io.scarman.spotify.request

import com.softwaremill.sttp.asynchttpclient.future.AsyncHttpClientFutureBackend

import scala.concurrent.ExecutionContext

object PlatformSpec {
  implicit def executionContext: ExecutionContext = ExecutionContext.Implicits.global
  implicit val backend                            = AsyncHttpClientFutureBackend()
}
