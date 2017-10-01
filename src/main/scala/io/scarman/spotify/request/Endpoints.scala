package io.scarman.spotify.request

import dispatch._

object Endpoints {
  final private val base: Req    = host("api.spotify.com").secure
  final private val version: Req = base / "v1"

  final val Token: Req         = host("accounts.spotify.com").secure / "api" / "token"
  final val Albums: Req        = version / "albums"
  final val Artists: Req       = version / "artists"
  final val Tracks: Req        = version / "tracks"
  final val AudioFeatures: Req = version / "audio-features"
  final val AudioAnalysis: Req = version / "audio-analysis"
}
