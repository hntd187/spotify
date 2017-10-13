package io.scarman.spotify.response

import java.nio.file.{Path, Paths}

import dispatch.Future

import io.scarman.spotify.http.{DownloadResults, FileDownload}

case class Image(height: Int, url: String, width: Int) extends FileDownload {
  def download(location: String): Future[DownloadResults]                                = download(Paths.get(location))
  def download(location: String, checksum: Option[String]): Future[DownloadResults]      = download(Paths.get(location), checksum)
  def download(location: Path, checksum: Option[String] = None): Future[DownloadResults] = downloadFile(url, location, checksum)
}
