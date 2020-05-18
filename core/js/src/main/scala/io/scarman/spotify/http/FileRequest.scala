package io.scarman.spotify.http

import io.scarman.spotify.request.Backend
import sttp.client._
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {

  protected def downloadFile(
      fileUrl: String,
      outputLocation: String // Not relevant in a browser
  )(implicit b: Backend, ec: ExecutionContext = ExecutionContext.Implicits.global): Future[DownloadResults] = {
    logger.debug(s"Requesting Image from: $fileUrl")
    basicRequest.get(uri"$fileUrl").response(asByteArray).send().map { response =>
      response.body match {
        case Right(bytes) if response.is200 => DownloadResults(None, Downloaded, bytes)
        case Left(_)                        => DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
