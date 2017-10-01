package io.scarman.spotify.response

import java.nio.file.{Path, Paths}

import dispatch.Future

import io.scarman.spotify.http.FileDownload

case class Image(height: Int, url: String, width: Int) extends FileDownload {

  def download(location: String): Future[Option[String]] = download(location, None)
  def download(location: Path): Future[Option[String]]   = download(location, None)

  def download(location: String, checksum: Option[String]): Future[Option[String]] = download(Paths.get(location), checksum)
  def download(location: Path, checksum: Option[String]): Future[Option[String]]   = downloadFile(url, location, checksum)
}
