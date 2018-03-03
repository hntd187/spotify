package io.scarman.spotify.request
import fr.hmil.roshttp.Protocol.HTTPS
import fr.hmil.roshttp.HttpRequest
object Endpoints {
  final val base          = HttpRequest().withProtocol(HTTPS).withHost("api.spotify.com")
  final val Token         = HttpRequest().withProtocol(HTTPS).withHost("accounts.spotify.com").withPath("/api/token")
  final val Albums        = "/v1/albums"
  final val Artists       = "/v1/artists"
  final val Tracks        = "/v1/tracks"
  final val AudioFeatures = "/v1/audio-features"
  final val AudioAnalysis = "/v1/audio-analysis"
}
