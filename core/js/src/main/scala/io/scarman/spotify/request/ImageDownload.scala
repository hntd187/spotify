package io.scarman.spotify.request

import io.scarman.spotify.http._

import scala.concurrent.Future

case class ImageDownload(url: String, outputLocation: String, checksum: Option[String])(implicit backend: Backend) extends FileRequest {

  def apply(): Future[DownloadResults] = downloadFile(url, outputLocation)

  def download(): Future[DownloadResults] = downloadFile(url, outputLocation)

}
