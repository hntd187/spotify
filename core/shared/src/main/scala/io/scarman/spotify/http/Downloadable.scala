package io.scarman.spotify.http

sealed trait DownloadResult

case object Downloaded extends DownloadResult
case class Failed(reason: String) extends DownloadResult

case class DownloadResults(checksum: Option[String], result: DownloadResult, bytes: Array[Byte] = Array.emptyByteArray)
