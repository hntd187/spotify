package io.scarman.spotify.http

import scala.concurrent.Future

sealed trait DownloadResult

case object Downloaded    extends DownloadResult
case object AlreadyExists extends DownloadResult

case class DownloadResults(checksum: Option[String], result: DownloadResult)

trait Downloadable {
  protected def downloadFile(fileUrl: String, outputLocation: String, checksum: Option[String]): Future[DownloadResults]
}
