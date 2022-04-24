package io.scarman.spotify.request

enum AlbumType {
  /// "album" means the full album
  case single, album, appears_on, compilation
}
object AlbumType {
  def default: List[AlbumType] = List(single, album, appears_on, compilation)
}
