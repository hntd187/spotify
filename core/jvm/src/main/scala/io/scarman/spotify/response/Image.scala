package io.scarman.spotify.response

import io.scarman.spotify.http.DownloadResults
import io.scarman.spotify.request.ImageDownload
import sttp.client._

import scala.concurrent.{ExecutionContext, Future}

case class Image(height: Int, url: String, width: Int) {

  def download(location: String)(implicit backend: SttpBackend[Future, Nothing, Nothing], ec: ExecutionContext): Future[DownloadResults] = {
    download(location, None)
  }

  def download(location: String, checksum: Option[String])(implicit backend: SttpBackend[Future, Nothing, Nothing],
                                                           ec: ExecutionContext): Future[DownloadResults] = {
    ImageDownload(url, location, checksum).download()
  }
}
