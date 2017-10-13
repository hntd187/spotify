package io.scarman.spotify.http

import java.nio.file.StandardOpenOption._
import java.nio.file._
import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._
import dispatch.as.File
import org.slf4j.LoggerFactory

import io.scarman.spotify.util.Checksum._

sealed trait DownloadResult

case object Downloaded    extends DownloadResult
case object AlreadyExists extends DownloadResult

case class DownloadResults(checksum: Option[String], result: DownloadResult)

private[spotify] trait FileDownload {
  protected val logger   = LoggerFactory.getLogger(getClass)
  protected val fileOpts = Array[StandardOpenOption](TRUNCATE_EXISTING, CREATE, WRITE)

  protected def downloadFile(fileUrl: String, outputLocation: Path, checksum: Option[String]): Future[DownloadResults] = {
    val download = checksum.exists(sum => verifyChecksum(outputLocation, sum))
    logger.debug(s"Download is: $download")
    if (download) {
      logger.info(s"File $outputLocation already exists and matches checksum...skipping...")
      Future.successful(DownloadResults(checksum, AlreadyExists))
    } else {
      val req    = url(fileUrl)
      val handle = File(outputLocation.toFile)
      logger.info(s"Requesting image at ${req.url}")
      for (_ <- Http.default(url(fileUrl) > handle))
        yield {
          val fileBytes = Files.readAllBytes(outputLocation)
          logger.debug(s"Image is ${fileBytes.length} bytes...")
          DownloadResults(Some(fileBytes.sha1), Downloaded)
        }
    }
  }
}
