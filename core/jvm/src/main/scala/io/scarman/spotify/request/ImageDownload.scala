package io.scarman.spotify.request

import io.scarman.spotify.http.{DownloadResults, FileRequest}

import scala.annotation.targetName
import scala.concurrent.{ExecutionContext, Future}

case class ImageDownload(url: String, outputLocation: String, checksum: Option[String])(using ec: ExecutionContext, b: Backend)
    extends FileRequest {

  def apply(): Future[DownloadResults] = downloadFile(url, outputLocation)

  def download(): Future[DownloadResults] = apply()

}
