package io.scarman.spotify.request

import io.scarman.spotify.*
import io.scarman.spotify.http.{Authorization, HttpRequest}
import sttp.client3.*

import scala.concurrent.ExecutionContext

/** A artist (such as Mr. Worldwide) https://developer.spotify.com/web-api/get-artist/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Artist(id: String, market: String = "ES")(implicit auth: Authorization, backend: Backend, ec: ExecutionContext)
    extends HttpRequest[response.Artist] {

  lazy protected val reqUri = uri"$base$AR/$id"

  def albums(t: AlbumType, types: AlbumType*): ArtistAlbums                                               = albums(types.toList :+ t)
  def albums(types: List[AlbumType] = AlbumTypes.default, limit: Int = 10, offset: Int = 0): ArtistAlbums = {
    ArtistAlbums(id, market, types, limit, offset)
  }

  def topTracks(): TopTracks = {
    TopTracks(id, market)
  }

  def relatedArtists(): RelatedArtists = {
    RelatedArtists(id)
  }
}
