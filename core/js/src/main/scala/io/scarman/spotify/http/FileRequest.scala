package io.scarman.spotify.http

import com.softwaremill.sttp._
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {
  implicit val backend: SttpBackend[Future, Nothing]
  implicit val execution: ExecutionContext

  protected def downloadFile(fileUrl: String, outputLocation: String): Future[DownloadResults] = {
    logger.info(s"Requesting Image from: $fileUrl")
    sttp.get(uri"$fileUrl").response(asByteArray).send().map { response =>
      if (response.is200) {
        DownloadResults(None, Downloaded, response.unsafeBody)
      } else {
        DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
