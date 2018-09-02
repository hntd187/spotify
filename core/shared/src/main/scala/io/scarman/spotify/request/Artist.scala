package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.HttpRequest

import scala.concurrent.Future

/**
  * A artist (such as Mr. Worldwide)
  * https://developer.spotify.com/web-api/get-artist/
  *
  * @param id
  * @param market
  * @param spotify
  */
case class Artist(id: String, market: String = "ES")(implicit spotify: Spotify, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Artist] {

  lazy protected val reqUri = uri"$base$AR/$id"

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
