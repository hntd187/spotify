package io.scarman.spotify.request

import io.scarman.spotify.Spotify
import io.scarman.spotify.http.{DownloadResults, FileRequest}

import scala.concurrent.Future

case class ImageDownload(url: String, outputLocation: String, checksum: Option[String])(implicit val spotify: Spotify) extends FileRequest {
  implicit val backend   = spotify.backend
  implicit val execution = spotify.execution

  def apply(): Future[DownloadResults] = downloadFile(url, outputLocation)

  def download(): Future[DownloadResults] = downloadFile(url, outputLocation)

}
