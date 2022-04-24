package io.scarman.spotify.request

import sttp.client3.FetchBackend
import org.scalajs.macrotaskexecutor.MacrotaskExecutor

import scala.concurrent.ExecutionContext

object PlatformSpec {
  implicit def executionContext: ExecutionContext = MacrotaskExecutor.Implicits.global
  implicit val backend: Backend                   = FetchBackend()
}
