package io.scarman
package spotify

import io.scarman.spotify.auth.ClientCredentials
import io.scarman.spotify.http.Authorization
import io.scarman.spotify.request.TimeRange.MediumTerm
import io.scarman.spotify.request.{AlbumType, Backend, Categories, CategoryPlaylists, Search, TimeRange}
import scribe.Logging

import scala.concurrent.ExecutionContext

/** The main entrance point into the Spotify API. This is required for all API calls, but most times it's provided implicitly. If you don't
  * want to use this object implicitly it has a number of methods that wrap the request case classes.
  *
  * @param auth
  */
class Spotify(val auth: Authorization)(implicit val backend: Backend, val execution: ExecutionContext) extends Logging {

  private implicit val a: Authorization = auth

  def getArtist(id: String, market: String = "US"): Artist                                                                          = Artist(id, market)
  def getAlbum(id: String, market: String = "US"): Album                                                                            = Album(id, market)
  def getTrack(id: String, market: String = "US"): Track                                                                            = Track(id, market)
  def getArtists(ids: String*): Artists                                                                                             = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums                                                                                               = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks                                                                                               = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures                                                                                   = AudioFeatures(id)
  def getAudioAnalysis(id: String): AudioAnalysis                                                                                   = AudioAnalysis(id)
  def getCategories(country: Option[String] = None, locale: Option[String] = None, limit: Int = 20, offset: Int = 0): Categories    =
    Categories(country, locale, limit, offset)
  def getCategory(categoryId: String, country: Option[String] = None, locale: Option[String] = None): Category                      =
    Category(categoryId, country, locale)
  def getCategoryPlaylists(categoryId: String, country: Option[String] = None, limit: Int = 20, offset: Int = 0): CategoryPlaylists =
    CategoryPlaylists(categoryId, country, limit, offset)
  def getUsersTopTracks(limit: Int = 20, offset: Int = 0, timeRange: TimeRange = MediumTerm): UsersTopTracks                        =
    UsersTopTracks(limit, offset, timeRange)
  def getUsersTopArtists(limit: Int = 20, offset: Int = 0, timeRange: TimeRange = MediumTerm): UsersTopArtists                      =
    UsersTopArtists(limit, offset, timeRange)

  def search(
      q: String,
      cat: String,
      market: String = "US",
      limit: Int = 10,
      offset: Int = 0,
      include_external: Option[String] = None
  ): Search = {
    Search(q, cat, market, limit, offset, include_external)
  }

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)
  }

  def getAlbums(ids: List[String], market: String = "US", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)
  }

  def getTracks(ids: List[String], market: String = "US"): Tracks = {
    Tracks(ids, market)
  }

  def getAlbumTracks(id: String, market: String = "US", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)
  }

  def getArtistAlbums(
      id: String,
      market: String = "US",
      albumType: List[AlbumType] = AlbumTypes.default,
      limit: Int = 10,
      offset: Int = 0
  ): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)
  }
}

object Spotify {
  def apply(id: String, secret: String)(implicit backend: Backend, execution: ExecutionContext): Spotify = {
    apply(ClientCredentials(id, secret))
  }

  def apply(auth: Authorization)(implicit backend: Backend, execution: ExecutionContext): Spotify = {
    new Spotify(auth)
  }
}
