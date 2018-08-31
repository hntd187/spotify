package io.scarman.spotify.http

import scala.concurrent.ExecutionContext

import io.scarman.spotify.response.Paging

private[spotify] trait PagingRequest[R <: Paging[_]] extends LastResponse[R] { http: HttpRequest[R] =>

  protected implicit val execution: ExecutionContext

}
