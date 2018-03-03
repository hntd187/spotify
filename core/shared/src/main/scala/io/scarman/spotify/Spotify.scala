package io.scarman
package spotify

import scala.concurrent.Future

import scribe.Logging

import scala.concurrent.ExecutionContext.Implicits.global

import io.scarman.spotify.http.AuthToken
import io.scarman.spotify.request.BaseAlbumType
import io.scarman.spotify.response.AccessToken

/**
  * The main entrance point into the Spotify API. This is required for all API calls, but most times it's
  * provided implicitly. If you don't want to use this object implicitly it has a number of methods that
  * wrap the request case classes.
  *
  * @param id
  * @param secret
  */
class Spotify(id: String, secret: String) extends AuthToken with Logging {

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
  def getArtist(id: String, market: String = "ES"): Artist = Artist(id, market)(this)
  def getAlbum(id: String, market: String = "ES"): Album   = Album(id, market)(this)
  def getTrack(id: String, market: String = "ES"): Track   = Track(id, market)(this)
  def getArtists(ids: String*): Artists                    = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums                      = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks                      = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures          = AudioFeatures(id)(this)
  def getAudioAnalysis(id: String): AudioAnalysis          = AudioAnalysis(id)(this)

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)(this)
  }

  def getAlbums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)(this)
  }

  def getTracks(ids: List[String], market: String = "ES"): Tracks = {
    Tracks(ids, market)(this)
  }

  def getAlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)(this)
  }

  def getArtistAlbums(id: String,
                      market: String = "ES",
                      albumType: List[BaseAlbumType] = AlbumTypes.default,
                      limit: Int = 10,
                      offset: Int = 0): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)(this)
  }
}

object Spotify {
  def apply(id: String, secret: String): Spotify = {
    new Spotify(id, secret)
  }
}
