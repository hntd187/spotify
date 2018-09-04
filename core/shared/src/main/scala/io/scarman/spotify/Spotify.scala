package io.scarman
package spotify

import com.softwaremill.sttp._
import io.scarman.spotify.http.AuthToken
import io.scarman.spotify.request.BaseAlbumType
import io.scarman.spotify.response.AccessToken

import scala.concurrent.{ExecutionContext, Future}
import java.util.concurrent.atomic.AtomicReference

/**
  * The main entrance point into the Spotify API. This is required for all API calls, but most times it's
  * provided implicitly. If you don't want to use this object implicitly it has a number of methods that
  * wrap the request case classes.
  *
  * @param id
  * @param secret
  */
class Spotify(id: String, secret: String)(implicit val backend: SttpBackend[Future, Nothing],
                                          val execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends AuthToken {

  private val token: AtomicReference[Future[AccessToken]] = new AtomicReference[Future[AccessToken]](getToken(id, secret))

  private[spotify] def getToken: Future[String] = {
    for {
      t <- token.get()
    } yield {
      if (t.isExpired) {
        logger.debug("Token expired, requesting new one...")
        token.set(getToken(id, secret))
      }
      logger.debug(s"Providing Token: ${t.access_token}")
      t.access_token
    }
  }

  def isExpired: Future[Boolean] = {
    for {
      t <- token.get()
    } yield t.isExpired
  }

  def getArtist(id: String, market: String = "ES"): Artist = Artist(id, market)(this, backend)
  def getAlbum(id: String, market: String = "ES"): Album   = Album(id, market)(this, backend)
  def getTrack(id: String, market: String = "ES"): Track   = Track(id, market)(this, backend)
  def getArtists(ids: String*): Artists                    = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums                      = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks                      = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures          = AudioFeatures(id)(this, backend)
  def getAudioAnalysis(id: String): AudioAnalysis          = AudioAnalysis(id)(this, backend)

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)(this, backend)
  }

  def getAlbums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)(this, backend)
  }

  def getTracks(ids: List[String], market: String = "ES"): Tracks = {
    Tracks(ids, market)(this, backend)
  }

  def getAlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)(this, backend)
  }

  def getArtistAlbums(id: String,
                      market: String = "ES",
                      albumType: List[BaseAlbumType] = AlbumTypes.default,
                      limit: Int = 10,
                      offset: Int = 0): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)(this, backend)
  }
}

object Spotify {
  def apply(id: String, secret: String)(implicit backend: SttpBackend[Future, Nothing]): Spotify = {
    new Spotify(id, secret)(backend)
  }
}
