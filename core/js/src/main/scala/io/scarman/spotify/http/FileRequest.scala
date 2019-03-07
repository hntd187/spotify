package io.scarman.spotify.http

import com.softwaremill.sttp._
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {

  protected def downloadFile(fileUrl: String, outputLocation: String)(
      implicit b: SttpBackend[Future, Nothing],
      ec: ExecutionContext = ExecutionContext.Implicits.global): Future[DownloadResults] = {
    logger.debug(s"Requesting Image from: $fileUrl")
    sttp.get(uri"$fileUrl").response(asByteArray).send().map { response =>
      if (response.is200) {
        DownloadResults(None, Downloaded, response.unsafeBody)
      } else {
        DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
