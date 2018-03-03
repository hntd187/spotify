package io.scarman.spotify.http

import java.nio.file.StandardOpenOption._
import java.nio.file._
import scala.concurrent.Future

import fr.hmil.roshttp.{HttpRequest => HR}
import monix.execution.Scheduler.Implicits.global
import scribe.Logging

import io.scarman.spotify.util.Checksum._

private[spotify] trait FileDownload extends Downloadable with Logging {
  protected val fileOpts = Array[StandardOpenOption](TRUNCATE_EXISTING, CREATE, WRITE)

  protected def downloadFile(fileUrl: String, outputLocation: String, checksum: Option[String]): Future[DownloadResults] = {
    val outputPath = Paths.get(outputLocation)
    val download   = checksum.exists(sum => verifyChecksum(outputPath, sum))
    logger.debug(s"Download is: $download")
    if (download) {
      logger.info(s"File $outputLocation already exists and matches checksum...skipping...")
      Future.successful(DownloadResults(checksum, AlreadyExists))
    } else {
      val chan = Files.newByteChannel(outputPath, fileOpts: _*)
      logger.info(s"Requesting image at $fileUrl")
      HR(fileUrl).stream().map { r =>
        r.body.foreach(chan.write)
        chan.close()
        DownloadResults(Some(Files.readAllBytes(outputPath).sha1), Downloaded)
      }
    }
  }
}
