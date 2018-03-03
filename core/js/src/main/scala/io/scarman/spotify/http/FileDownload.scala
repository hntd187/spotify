package io.scarman.spotify.http

import scala.concurrent.Future

trait FileDownload extends Downloadable {
  protected def downloadFile(fileUrl: String, outputLocation: String, checksum: Option[String]): Future[DownloadResults] = ???
}
