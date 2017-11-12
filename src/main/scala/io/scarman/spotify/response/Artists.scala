package io.scarman.spotify.response

case class Artists(artists: List[Artist]) {
  def apply(): List[Artist] = artists
  def apply(i: Int): Artist = artists(i)
}
