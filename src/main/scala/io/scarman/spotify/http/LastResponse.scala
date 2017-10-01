package io.scarman.spotify.http

private[spotify] trait LastResponse[R] {
  protected var lastResponse: Option[R] = None
}
