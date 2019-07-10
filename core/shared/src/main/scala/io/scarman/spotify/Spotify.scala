package io.scarman
package spotify

import com.softwaremill.sttp._
import io.scarman.spotify.auth.ClientCredentials
import io.scarman.spotify.http.Authorization
import io.scarman.spotify.request.BaseAlbumType
import scribe.Logging

import scala.concurrent.{ExecutionContext, Future}

/**
  * The main entrance point into the Spotify API. This is required for all API calls, but most times it's
  * provided implicitly. If you don't want to use this object implicitly it has a number of methods that
  * wrap the request case classes.
  *
  * @param id
  * @param secret
  */
class Spotify(val auth: Authorization)(implicit val backend: SttpBackend[Future, Nothing],
                                       val execution: ExecutionContext = ExecutionContext.Implicits.global)
    extends Logging {

  def getArtist(id: String, market: String = "ES"): Artist = Artist(id, market)(auth, backend)
  def getAlbum(id: String, market: String  = "ES"): Album  = Album(id, market)(auth, backend)
  def getTrack(id: String, market: String  = "ES"): Track  = Track(id, market)(auth, backend)
  def getArtists(ids: String*): Artists           = getArtists(ids.toList)
  def getAlbums(ids: String*): Albums             = getAlbums(ids.toList)
  def getTracks(ids: String*): Tracks             = getTracks(ids.toList)
  def getAudioFeatures(id: String): AudioFeatures = AudioFeatures(id)(auth, backend)
  def getAudioAnalysis(id: String): AudioAnalysis = AudioAnalysis(id)(auth, backend)

  def getArtists(ids: List[String], limit: Int = 10, offset: Int = 0): Artists = {
    Artists(ids, limit, offset)(auth, backend)
  }

  def getAlbums(ids: List[String], market: String = "ES", limit: Int = 10, offset: Int = 0): Albums = {
    Albums(ids, market, limit, offset)(auth, backend)
  }

  def getTracks(ids: List[String], market: String = "ES"): Tracks = {
    Tracks(ids, market)(auth, backend)
  }

  def getAlbumTracks(id: String, market: String = "ES", limit: Int = 10, offset: Int = 0): AlbumTracks = {
    AlbumTracks(id, market, limit, offset)(auth, backend)
  }

  def getArtistAlbums(id: String,
                      market: String                 = "ES",
                      albumType: List[BaseAlbumType] = AlbumTypes.default,
                      limit: Int                     = 10,
                      offset: Int                    = 0): ArtistAlbums = {
    ArtistAlbums(id, market, albumType, limit, offset)(auth, backend)
  }
}

object Spotify {
  def apply(id: String, secret: String)(implicit backend: SttpBackend[Future, Nothing], execution: ExecutionContext): Spotify = {
    apply(ClientCredentials(id, secret))
  }

  def apply(auth: Authorization)(implicit backend: SttpBackend[Future, Nothing],
                                 execution: ExecutionContext = ExecutionContext.Implicits.global): Spotify = {
    new Spotify(auth)
  }
}
