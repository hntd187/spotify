package io.scarman.spotify.util

import java.nio.file.{Files, Path}
import java.security.MessageDigest

import org.slf4j.{Logger, LoggerFactory}

/**
  * Some utilities to assist in calculating the checksums of album covers downloaded
  */
object Checksum {

  private val logger: Logger    = LoggerFactory.getLogger(getClass)
  private val md: MessageDigest = MessageDigest.getInstance("SHA-1")

  def SHA1Sum(bytes: Array[Byte]): Array[Byte] = md.digest(bytes)

  def verifyChecksum(filePath: Path, sum: String): Boolean = {
    if (filePath.toFile.exists()) {
      val fileSha1: String = Files.readAllBytes(filePath).sha1.string
      logger.debug(s"$sum == $fileSha1 (${fileSha1 == sum})")
      !(fileSha1 == sum)
    } else {
      true
    }
  }

  implicit def bytesToSha(bytes: Array[Byte]): String = bytes.string

  implicit class RichSha1(sha: Array[Byte]) {
    def string: String = {
      val formatter = new StringBuilder()
      sha.foreach { b =>
        formatter.append(f"$b%02x")
      }
      logger.info(s"SHA1 sum is ${formatter.result()}")
      formatter.result()
    }
    def sha1: Array[Byte] = Checksum.SHA1Sum(sha)
  }

}
