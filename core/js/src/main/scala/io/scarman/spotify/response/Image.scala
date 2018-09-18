package io.scarman.spotify.response

import com.softwaremill.sttp._
import io.scarman.spotify.Spotify
import io.scarman.spotify.request.ImageDownload

import scala.concurrent.Future

case class Image(height: Int, url: String, width: Int) {

  def download()(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing]): ImageDownload = {
    ImageDownload(url, "", None)
  }

}
