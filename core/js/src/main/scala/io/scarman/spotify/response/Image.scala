package io.scarman.spotify.response

import io.scarman.spotify.request.{Backend, ImageDownload}

case class Image(height: Int, url: String, width: Int) {

  def download()(implicit backend: Backend): ImageDownload = {
    ImageDownload(url, "", None)
  }

}
