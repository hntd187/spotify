package io.scarman.spotify.request

import dispatch.Req

import io.scarman.spotify.http.HttpRequest
import io.scarman.spotify.Spotify
import io.scarman.spotify.response

/**
  * A artist (such as Mr. Worldwide)
  * https://developer.spotify.com/web-api/get-artist/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Artist(id: String, market: String = "ES")(implicit spotify: Spotify) extends HttpRequest[response.Artist] {
  lazy protected val request: Req = Endpoints.Artists / id

  def albums(t: BaseAlbumType, types: BaseAlbumType*): ArtistAlbums = albums(types.toList :+ t)
  def albums(types: List[BaseAlbumType] = AlbumTypes.default, limit: Int = 10, offset: Int = 0): ArtistAlbums = {
    ArtistAlbums(id, market, types, limit, offset)
  }

  def topTracks(): TopTracks = {
    TopTracks(id, market)
  }

  def relatedArtists(): RelatedArtists = {
    RelatedArtists(id)
  }
}
