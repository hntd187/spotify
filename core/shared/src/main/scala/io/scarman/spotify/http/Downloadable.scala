package io.scarman.spotify.http

import scala.concurrent.Future

sealed trait DownloadResult

case object Downloaded            extends DownloadResult
case class Failed(reason: String) extends DownloadResult

case class DownloadResults(checksum: Option[String], result: DownloadResult, bytes: Array[Byte] = Array.emptyByteArray)

trait Downloadable {
  protected def downloadFile(fileUrl: String, outputLocation: String): Future[DownloadResults]
}
