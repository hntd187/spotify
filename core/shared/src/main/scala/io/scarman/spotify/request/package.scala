package io.scarman.spotify

import sttp.client._
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.scarman.spotify.http.{DownloadResult, DownloadResults}
import io.scarman.spotify.{response => r}

import scala.concurrent.Future

package object request {
  type Backend      = sttp.client.SttpBackend[Future, Nothing, NothingT]
  type AlbumPage    = r.Paging[r.ArtistAlbum]
  type TrackPage    = r.Paging[r.Track]
  type ArtistPage   = r.Paging[r.Artist]
  type PlaylistPage = r.Paging[r.Playlist]
  type CategoryPage = r.Paging[r.Category]

  val base     = uri"https://api.spotify.com/v1/"
  val acc      = uri"https://accounts.spotify.com"
  val tokenUri = uri"$acc/api/token"
  val authUri  = uri"$acc/authorize"
  val AB       = "albums"
  val AR       = "artists"
  val TR       = "tracks"
  val AF       = "audio-features"
  val AA       = "audio-analysis"

  @inline
  def transformNames: String => String = {
    case "tpe" => "type"
    case x     => x
  }

  implicit val decoderConfig = Configuration(transformNames, transformNames, true, None)

  implicit val atokd: Decoder[r.AccessToken]    = deriveConfiguredDecoder
  implicit val d: Decoder[r.Album]              = deriveConfiguredDecoder
  implicit val ad: Decoder[r.Artist]            = deriveConfiguredDecoder
  implicit val eu: Decoder[r.ExternalUrl]       = deriveConfiguredDecoder
  implicit val fd: Decoder[r.Followers]         = deriveConfiguredDecoder
  implicit val idd: Decoder[r.Image]            = deriveConfiguredDecoder
  implicit val fdrr: Decoder[DownloadResult]    = deriveConfiguredDecoder
  implicit val fdr: Decoder[DownloadResults]    = deriveConfiguredDecoder
  implicit val cd: Decoder[r.Copyright]         = deriveConfiguredDecoder
  implicit val eid: Decoder[r.ExternalId]       = deriveConfiguredDecoder
  implicit val td: Decoder[r.Track]             = deriveConfiguredDecoder
  implicit val afd: Decoder[r.AudioFeatures]    = deriveConfiguredDecoder
  implicit val aad: Decoder[r.AudioAnalysis]    = deriveConfiguredDecoder
  implicit val bd: Decoder[r.Bar]               = deriveConfiguredDecoder
  implicit val btd: Decoder[r.Beat]             = deriveConfiguredDecoder
  implicit val sd: Decoder[r.Section]           = deriveConfiguredDecoder
  implicit val ssd: Decoder[r.Segment]          = deriveConfiguredDecoder
  implicit val tmd: Decoder[r.Tatum]            = deriveConfiguredDecoder
  implicit val atd: Decoder[r.AnalysisTrack]    = deriveConfiguredDecoder
  implicit val aatd: Decoder[r.ArtistAlbum]     = deriveConfiguredDecoder
  implicit val apd: Decoder[AlbumPage]          = deriveConfiguredDecoder
  implicit val tpd: Decoder[TrackPage]          = deriveConfiguredDecoder
  implicit val artpd: Decoder[ArtistPage]       = deriveConfiguredDecoder
  implicit val ppd: Decoder[PlaylistPage]       = deriveConfiguredDecoder
  implicit val trd: Decoder[r.Tracks]           = deriveConfiguredDecoder
  implicit val trr: Decoder[r.TracksRef]        = deriveConfiguredDecoder
  implicit val asd: Decoder[r.Albums]           = deriveConfiguredDecoder
  implicit val ars: Decoder[r.Artists]          = deriveConfiguredDecoder
  implicit val tld: Decoder[r.TrackLink]        = deriveConfiguredDecoder
  implicit val sadd: Decoder[r.SimpleAlbum]     = deriveConfiguredDecoder
  implicit val plrd: Decoder[r.Playlist]        = deriveConfiguredDecoder
  implicit val plsd: Decoder[r.Playlists]       = deriveConfiguredDecoder
  implicit val urd: Decoder[r.User]             = deriveConfiguredDecoder
  implicit val srd: Decoder[r.SearchResults]    = deriveConfiguredDecoder
  implicit val sid: Decoder[r.SharedImage]      = deriveConfiguredDecoder
  implicit val cad: Decoder[r.Category]         = deriveConfiguredDecoder
  implicit val cpp: Decoder[CategoryPage]       = deriveConfiguredDecoder
  implicit val csd: Decoder[r.Categories]       = deriveConfiguredDecoder
  implicit val pupd: Decoder[r.PrivateUser]     = deriveConfiguredDecoder
  implicit val ecd: Decoder[r.ExplicitContent]  = deriveConfiguredDecoder
  implicit val cpd: Decoder[r.CurrentlyPlaying] = deriveConfiguredDecoder
  implicit val ctxd: Decoder[r.Context]         = deriveConfiguredDecoder
}
