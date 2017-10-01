package io.scarman.spotify.http

import java.nio.file.StandardOpenOption._
import java.nio.file._
import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._
import dispatch.as.File
import org.slf4j.LoggerFactory

import io.scarman.spotify.util.Checksum._

private[spotify] trait FileDownload {
  protected val logger   = LoggerFactory.getLogger(getClass)
  protected val fileOpts = Array[StandardOpenOption](TRUNCATE_EXISTING, CREATE, WRITE)

  protected def downloadFile(fileUrl: String, outputLocation: Path, checksum: Option[String]): Future[Option[String]] = {
    val download = checksum.exists(sum => verifyChecksum(outputLocation, sum))
    if (download) {
      val req    = url(fileUrl)
      val handle = File(outputLocation.toFile)
      logger.info(s"Requesting image at ${req.url}")
      for (_ <- Http.default(url(fileUrl) > handle))
        yield Some(Files.readAllBytes(outputLocation).sha1)
    } else {
      logger.info(s"File $outputLocation already exists and matches checksum...skipping...")
      Future(checksum)
    }
  }
}
