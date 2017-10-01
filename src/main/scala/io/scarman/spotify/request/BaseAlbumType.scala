package io.scarman.spotify.request

sealed trait BaseAlbumType

case object single extends BaseAlbumType

case object fullalbum extends BaseAlbumType {
  override def toString: String = "album"
}

case object appears_on extends BaseAlbumType

case object compilation extends BaseAlbumType

object AlbumTypes {
  def default: List[BaseAlbumType] = List(single, fullalbum, appears_on, compilation)
}
