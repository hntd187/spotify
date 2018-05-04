package io.scarman.spotify.http

import io.circe.DecodingFailure

private[spotify] trait LastResponse[R] {
  protected var lastResponse: Either[DecodingFailure, R] = Left(null)
}
