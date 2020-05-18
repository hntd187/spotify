package io.scarman
package spotify

import io.scarman.spotify.auth.ClientCredentials
import io.scarman.spotify.http.Authorization
import io.scarman.spotify.request.TimeRange.MediumTerm
import io.scarman.spotify.request.{BaseAlbumType, Categories, CategoryPlaylists, Search, Backend, TimeRange}
import scribe.Logging

import scala.concurrent.ExecutionContext

/**
  * The main entrance point into the Spotify API. This is required for all API calls, but most times it's
  * provided implicitly. If you don't want to use this object implicitly it has a number of methods that
  * wrap the request case classes.
  *
  * @param auth
  */
class Spotify(val auth: Authorization)(implicit val backend: Backend, val execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends Logging {

  def getArtist(id: String, market: String = "US"): Artist = Artist(id, market)(auth, backend)
  def getAlbum(id: String, market: String  = "US"): Album  = Album(id, market)(auth, backend)
  def getTrack(id: String, market: String  = "US"): Track  = Track(id, market)(auth, backend)
  def getArtists(ids: String*): Artists           = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums             = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks             = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures = AudioFeatures(id)(auth, backend)
  def getAudioAnalysis(id: String): AudioAnalysis = AudioAnalysis(id)(auth, backend)
  def getCategories(country: Option[String] = None, locale: Option[String] = None, limit: Int = 20, offset: Int = 0): Categories =
    Categories(country, locale, limit, offset)(auth, backend)
  def getCategory(categoryId: String, country: Option[String] = None, locale: Option[String] = None): Category =
    Category(categoryId, country, locale)(auth, backend)
  def getCategoryPlaylists(categoryId: String, country: Option[String] = None, limit: Int = 20, offset: Int = 0): CategoryPlaylists =
    CategoryPlaylists(categoryId, country, limit, offset)(auth, backend)
  def getUsersTopTracks(limit: Int = 20, offset: Int = 0, timeRange: TimeRange = MediumTerm): UsersTopTracks =
    UsersTopTracks(limit, offset, timeRange)(auth, backend)
  def getUsersTopArtists(limit: Int = 20, offset: Int = 0, timeRange: TimeRange = MediumTerm): UsersTopArtists =
    UsersTopArtists(limit, offset, timeRange)(auth, backend)

  def search(q: String,
             cat: String,
             market: String                   = "US",
             limit: Int                       = 10,
             offset: Int                      = 0,
             include_external: Option[String] = None): Search = {
    Search(q, cat, market, limit, offset, include_external)(auth, backend)
  }

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)(auth, backend)
  }

  def getAlbums(ids: List[String], market: String = "US", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)(auth, backend)
  }

  def getTracks(ids: List[String], market: String = "US"): Tracks = {
    Tracks(ids, market)(auth, backend)
  }

  def getAlbumTracks(id: String, market: String = "US", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)(auth, backend)
  }

  def getArtistAlbums(id: String,
                      market: String                 = "US",
                      albumType: List[BaseAlbumType] = AlbumTypes.default,
                      limit: Int                     = 10,
                      offset: Int                    = 0): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)(auth, backend)
  }
}

object Spotify {
  def apply(id: String, secret: String)(implicit backend: Backend, execution: ExecutionContext): Spotify = {
    apply(ClientCredentials(id, secret))
  }

  def apply(auth: Authorization)(implicit backend: Backend, execution: ExecutionContext = ExecutionContext.Implicits.global): Spotify = {
    new Spotify(auth)
  }
}
