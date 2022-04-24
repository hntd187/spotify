package io.scarman.spotify.response

/** @param url
  *   location of the image
  * @param height
  *   in pixels, occasionally I have found them null
  * @param width
  *   in pixels, occasionally I have found them null
  */
case class SharedImage(url: String, height: Option[Int], width: Option[Int])
