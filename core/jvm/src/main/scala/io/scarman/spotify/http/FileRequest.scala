package io.scarman.spotify.http

import java.nio.file.{Files, Paths}

import com.softwaremill.sttp._
import io.scarman.spotify.util.Checksum._
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

trait FileRequest extends Logging {

  implicit val backend: SttpBackend[Future, Nothing]
  implicit val execution: ExecutionContext

  protected def downloadFile(fileUrl: String, outputLocation: String): Future[DownloadResults] = {
    val file = Paths.get(outputLocation)
    sttp.get(uri"$fileUrl").response(asPath(file, overwrite = true)).send().map { response =>
      if (response.is200) {
        DownloadResults(Some(Files.readAllBytes(file).sha1), Downloaded)
      } else {
        DownloadResults(None, Failed(response.statusText))
      }
    }
  }
}
