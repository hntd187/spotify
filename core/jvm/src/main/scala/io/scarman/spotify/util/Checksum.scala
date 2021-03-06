package io.scarman.spotify.util

import java.security.MessageDigest

import scribe.Logging

/**
  * Some utilities to assist in calculating the checksums of album covers downloaded
  */
object Checksum extends Logging {

  private val md: MessageDigest = MessageDigest.getInstance("SHA-256")

  def SHA256Sum(bytes: Array[Byte]): Array[Byte] = md.digest(bytes)

  implicit def bytesToSha(bytes: Array[Byte]): String = bytes.string

  implicit class RichSha256(sha: Array[Byte]) {
    def string: String = {
      val formatter = new StringBuilder()
      sha.foreach { b =>
        formatter.append(f"$b%02x")
      }
      logger.debug(s"SHA256 sum is ${formatter.result()}")
      formatter.result()
    }
    def sha256: Array[Byte] = Checksum.SHA256Sum(sha)
  }

}
