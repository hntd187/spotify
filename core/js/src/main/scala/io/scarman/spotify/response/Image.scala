package io.scarman.spotify.response

import scala.concurrent.Future

import io.scarman.spotify.http._

case class Image(height: Int, url: String, width: Int) extends FileDownload {
  def download(location: String): Future[DownloadResults]                           = download(location, None)
  def download(location: String, checksum: Option[String]): Future[DownloadResults] = downloadFile(url, location, checksum)
}
