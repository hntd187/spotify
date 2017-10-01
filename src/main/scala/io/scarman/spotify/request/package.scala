package io.scarman.spotify

package object request {
  type AlbumPage = response.Paging[response.ArtistAlbum]
  type TrackPage = response.Paging[response.Track]
}
