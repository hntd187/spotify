package io.scarman.spotify.request
import com.softwaremill.sttp.SttpBackend
import io.scarman.spotify.http.{DownloadResults, FileRequest}

import scala.concurrent.{ExecutionContext, Future}

case class ImageDownload(url: String, outputLocation: String, checksum: Option[String])(implicit backend: SttpBackend[Future, Nothing],
                                                                                        ec: ExecutionContext)
    extends FileRequest {

  def apply(): Future[DownloadResults] = downloadFile(url, outputLocation)

  def download(): Future[DownloadResults] = apply()

}
