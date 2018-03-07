package io.scarman
package spotify

import scala.concurrent.Future

import scribe.Logging
import io.scarman.spotify.http.AuthToken
import io.scarman.spotify.request.BaseAlbumType
import io.scarman.spotify.response.AccessToken
import monix.execution.Scheduler

/**
  * The main entrance point into the Spotify API. This is required for all API calls, but most times it's
  * provided implicitly. If you don't want to use this object implicitly it has a number of methods that
  * wrap the request case classes.
  *
  * @param id
  * @param secret
  */
class Spotify(id: String, secret: String)(implicit val scheduler: Scheduler) extends AuthToken with Logging {

  private var token: Future[AccessToken] = getToken(id, secret)

  private[spotify] def getToken: Future[String] = {
    for {
      t <- token
    } yield {
      if (t.isExpired) {
        logger.info("Token expired, requesting new one...")
        token = getToken(id, secret)
      }
      t.access_token
    }
  }

  def isExpired: Future[Boolean] = {
    for {
      t <- token
    } yield t.isExpired
  }
  def getArtist(id: String, market: String = "ES"): Artist = Artist(id, market)(this, scheduler)
  def getAlbum(id: String, market: String = "ES"): Album   = Album(id, market)(this, scheduler)
  def getTrack(id: String, market: String = "ES"): Track   = Track(id, market)(this, scheduler)
  def getArtists(ids: String*): Artists                    = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums                      = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks                      = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures          = AudioFeatures(id)(this, scheduler)
  def getAudioAnalysis(id: String): AudioAnalysis          = AudioAnalysis(id)(this, scheduler)

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)(this, scheduler)
  }

  def getAlbums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)(this, scheduler)
  }

  def getTracks(ids: List[String], market: String = "ES"): Tracks = {
    Tracks(ids, market)(this, scheduler)
  }

  def getAlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)(this, scheduler)
  }

  def getArtistAlbums(id: String,
                      market: String = "ES",
                      albumType: List[BaseAlbumType] = AlbumTypes.default,
                      limit: Int = 10,
                      offset: Int = 0): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)(this, scheduler)
  }
}

object Spotify {
  def apply(id: String, secret: String)(implicit scheduler: Scheduler): Spotify = {
    new Spotify(id, secret)(scheduler)
  }
}
