package io.scarman.spotify.http

private[spotify] trait LastResponse[R] {
  protected var lastResponse: Either[Throwable, R] = Left(new Throwable)
}
