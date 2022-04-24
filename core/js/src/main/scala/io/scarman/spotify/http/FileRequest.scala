package io.scarman.spotify.http

import io.scarman.spotify.request.Backend
import sttp.client3.*
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {

  protected def downloadFile(
      fileUrl: String,
      outputLocation: String // Not relevant in a browser
  )(implicit b: Backend, ec: ExecutionContext): Future[DownloadResults] = {
    logger.debug(s"Requesting Image from: $fileUrl")
    basicRequest.get(uri"$fileUrl").response(asByteArray).send(b).map { response =>
      response.body match {
        case Right(bytes) if response.is200 => DownloadResults(None, Downloaded, bytes)
        case Left(_)                        => DownloadResults(None, Failed(response.statusText))
        case e                              => throw new RuntimeException(s"Unknown exception occurred: ${e.toString}")
      }
    }
  }
}
