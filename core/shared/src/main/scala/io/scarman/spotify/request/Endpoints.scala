package io.scarman.spotify.request

import com.softwaremill.sttp._

object Endpoints {
  final val base          = uri"https://api.spotify.com"
  final val Token         = uri"https://accounts.spotify.com/api/token"
  final val Albums        = "/v1/albums"
  final val Artists       = "/v1/artists"
  final val Tracks        = "/v1/tracks"
  final val AudioFeatures = "/v1/audio-features"
  final val AudioAnalysis = "/v1/audio-analysis"
}
