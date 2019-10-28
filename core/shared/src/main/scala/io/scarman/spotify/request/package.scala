package io.scarman.spotify

import com.softwaremill.sttp._
import io.circe._
import io.circe.generic.semiauto._
import io.scarman.spotify.http.{DownloadResult, DownloadResults}
import io.scarman.spotify.{response => r}

package object request {
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

  implicit val atokd: Decoder[r.AccessToken] = deriveDecoder
  implicit val d: Decoder[r.Album]           = deriveDecoder
  implicit val ad: Decoder[r.Artist]         = deriveDecoder
  implicit val eu: Decoder[r.ExternalUrl]    = deriveDecoder
  implicit val fd: Decoder[r.Followers]      = deriveDecoder
  implicit val idd: Decoder[r.Image]         = deriveDecoder
  implicit val fdrr: Decoder[DownloadResult] = deriveDecoder
  implicit val fdr: Decoder[DownloadResults] = deriveDecoder
  implicit val cd: Decoder[r.Copyright]      = deriveDecoder
  implicit val eid: Decoder[r.ExternalId]    = deriveDecoder
  implicit val td: Decoder[r.Track]          = deriveDecoder
  implicit val afd: Decoder[r.AudioFeatures] = deriveDecoder
  implicit val aad: Decoder[r.AudioAnalysis] = deriveDecoder
  implicit val bd: Decoder[r.Bar]            = deriveDecoder
  implicit val btd: Decoder[r.Beat]          = deriveDecoder
  implicit val sd: Decoder[r.Section]        = deriveDecoder
  implicit val ssd: Decoder[r.Segment]       = deriveDecoder
  implicit val tmd: Decoder[r.Tatum]         = deriveDecoder
  implicit val atd: Decoder[r.AnalysisTrack] = deriveDecoder
  implicit val aatd: Decoder[r.ArtistAlbum]  = deriveDecoder
  implicit val apd: Decoder[AlbumPage]       = deriveDecoder
  implicit val tpd: Decoder[TrackPage]       = deriveDecoder
  implicit val artpd: Decoder[ArtistPage]    = deriveDecoder
  implicit val ppd: Decoder[PlaylistPage]    = deriveDecoder
  implicit val trd: Decoder[r.Tracks]        = deriveDecoder
  implicit val trr: Decoder[r.TracksRef]     = deriveDecoder
  implicit val asd: Decoder[r.Albums]        = deriveDecoder
  implicit val ars: Decoder[r.Artists]       = deriveDecoder
  implicit val tld: Decoder[r.TrackLink]     = deriveDecoder
  implicit val sadd: Decoder[r.SimpleAlbum]  = deriveDecoder
  implicit val plrd: Decoder[r.Playlist]     = deriveDecoder
  implicit val plsd: Decoder[r.Playlists]    = deriveDecoder
  implicit val urd: Decoder[r.User]          = deriveDecoder
  implicit val srd: Decoder[r.SearchResults] = deriveDecoder
  implicit val sid: Decoder[r.SharedImage]   = deriveDecoder
  implicit val cad: Decoder[r.Category]      = deriveDecoder
  implicit val cpp: Decoder[CategoryPage]    = deriveDecoder
  implicit val csd: Decoder[r.Categories]    = deriveDecoder
}
