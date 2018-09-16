package io.scarman.spotify.response

case class Albums(albums: List[Album]) {
  def apply(): List[Album] = albums
  def apply(i: Int): Album = albums(i)
}
