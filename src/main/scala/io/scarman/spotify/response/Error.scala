package io.scarman.spotify.response

case class ErrorCase(error: Error)
case class Error(status: Int, message: String)
