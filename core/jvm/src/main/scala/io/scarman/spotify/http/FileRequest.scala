package io.scarman.spotify.http

import io.scarman.spotify.request.Backend

import java.nio.file.{Files, Paths}
import io.scarman.spotify.util.Checksum.{*, given}
import scribe.Logging
import sttp.client3.*

import java.util
import java.util.stream.Collectors
import scala.concurrent.{ExecutionContext, Future}

trait FileRequest(using ec: ExecutionContext, b: Backend) extends Logging {

  protected def downloadFile(fileUrl: String, outputLocation: String): Future[DownloadResults] = {
    val file = Paths.get(outputLocation)
    basicRequest.get(uri"$fileUrl").response(asPath(file)).send(b).map { response =>
      if (response.is200) {
        DownloadResults(Some(Files.readAllBytes(file).sha256), Downloaded)
      } else {
        DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
