package io.scarman.spotify.http

import java.nio.file.{Files, Paths}

import io.scarman.spotify.util.Checksum._
import scribe.Logging
import sttp.client._

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {

  protected def downloadFile(fileUrl: String, outputLocation: String)(implicit b: SttpBackend[Future, Nothing, Nothing],
                                                                      ec: ExecutionContext): Future[DownloadResults] = {
    val file = Paths.get(outputLocation)
    basicRequest.get(uri"$fileUrl").response(asPath(file)).send().map { response =>
      if (response.is200) {
        DownloadResults(Some(Files.readAllBytes(file).sha256), Downloaded)
      } else {
        DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
