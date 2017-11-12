package io.scarman.spotify.response

case class Tracks(tracks: List[Track]) {
  def apply(): List[Track] = tracks
  def apply(i: Int): Track = tracks(i)
}
