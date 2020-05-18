package io.scarman.spotify.request

import io.scarman.spotify.response.SimpleAlbum
import scala.concurrent.duration.Duration

class TrackSpec extends UnitSpec {

  describe("Tests for Track Endpoints") {

    it("Should get a sweet Pitbull track") {

      val track = spotify.getTrack(trackId)
      track().map { result =>
        val album   = result.album.get
        val artists = result.artists

        album shouldBe a[SimpleAlbum]
        album.id shouldBe "3X33e7UII5loqrEgauOKEC"
        album.name shouldBe "Timber"

        artists.head.name shouldBe "Pitbull"
        artists.last.name shouldBe "Kesha"

        result.disc_number shouldBe 1
        result.duration_ms shouldBe 204053
        result.getDuration shouldBe a[Duration]
        result.getDuration.toSeconds shouldBe 204
        result.explicit shouldBe false
        result.is_playable.value shouldBe true
        result.name shouldBe "Timber"
        result.popularity.value shouldBe (66 +- 10)
      }

      track.getAudioAnalysis().map { result =>
        result.numBars shouldBe 108
        result.numTatums shouldBe 866
        result.numBeats shouldBe 433
        result.numSections shouldBe 10
        result.numSegments shouldBe 755

        result.averageBarLength should be(1.841694166666667 +- 0.01)
        result.averageBarConfidence should be(0.4981574074074074 +- 1.0)
        result.bars.head.getDuration shouldBe a[Duration]
      }

      track.getAudioFeatures().map { result =>
        result.danceability shouldBe 0.587
        result.energy shouldBe 0.965
        result.key shouldBe 11
        result.loudness shouldBe -4.106
        result.mode shouldBe 1
        result.speechiness shouldBe 0.101
        result.acousticness shouldBe 0.0362
        result.instrumentalness shouldBe 0
        result.liveness shouldBe 0.138
        result.valence shouldBe (0.818 +- 0.2)
        result.tempo shouldBe 129.972
        result.duration_ms shouldBe 204053
        result.getDuration shouldBe a[Duration]
        result.getDuration.toSeconds shouldBe 204
        result.time_signature shouldBe 4
      }
    }

    it("Should get multiple hot fire tracks from Pitbull") {
      val tracks = spotify.getTracks("6OmhkSOpvYBokMKQxpIGx2", "1zHlj4dQ8ZAtrayhuDDmkY")
      tracks().map { result =>
        result.tracks should have length 2
        result.tracks.head.name shouldBe "Global Warming (feat. Sensato)"
        result.tracks.last.name shouldBe "Timber (feat. Ke$ha)"
      }
    }

//    it("Should throw an exception on bad trackId") {
//      ScalaFutures.whenReady(spotify.getTrack("asdf1234")().failed) { e =>
//        e shouldBe an[Exception]
//      }
//    }
  }
}
