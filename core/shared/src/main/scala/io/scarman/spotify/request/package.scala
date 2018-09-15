package io.scarman.spotify

import com.softwaremill.sttp._
import io.circe._
import io.circe.generic.semiauto._
import io.scarman.spotify.http.{DownloadResult, DownloadResults}
import io.scarman.spotify.{response => r}

package object request {
  type AlbumPage = r.Paging[r.ArtistAlbum]
  type TrackPage = r.Paging[r.Track]

  final val base  = uri"https://api.spotify.com/v1/"
  final val Token = uri"https://accounts.spotify.com/api/token"
  final val AB    = "albums"
  final val AR    = "artists"
  final val TR    = "tracks"
  final val AF    = "audio-features"
  final val AA    = "audio-analysis"

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
  implicit val trd: Decoder[r.Tracks]        = deriveDecoder
  implicit val asd: Decoder[r.Albums]        = deriveDecoder
  implicit val ars: Decoder[r.Artists]       = deriveDecoder
  implicit val tld: Decoder[r.TrackLink]     = deriveDecoder
  implicit val sadd: Decoder[r.SimpleAlbum]  = deriveDecoder
}
