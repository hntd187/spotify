package io.scarman.spotify.request

import scala.concurrent.duration.Duration

class AudioSpec extends UnitSpec {
  import UnitSpec._

  describe("Tests for Audio Features and Analysis Endpoints") {
    it("Should get some sweet audio features from Mr. Worldwide") {

      val features = spotify.getAudioFeatures(id = trackId)
      val result   = await(features())

      result.danceability shouldBe 0.587
      result.energy shouldBe 0.965
      result.key shouldBe 11
      result.loudness shouldBe -4.106
      result.mode shouldBe 1
      result.speechiness shouldBe 0.101
      result.acousticness shouldBe 0.0362
      result.instrumentalness shouldBe 0
      result.liveness shouldBe 0.138
      result.valence shouldBe 0.818
      result.tempo shouldBe 129.972
      result.duration_ms shouldBe 204053
      result.getDuration shouldBe a[Duration]
      result.getDuration.toSeconds shouldBe 204
      result.time_signature shouldBe 4

    }

    it("Should get some fire analysis") {
      val analysis = spotify.getAudioAnalysis(id = trackId)
      val resp     = await(analysis())

      resp.numBars shouldBe 108
      resp.numTatums shouldBe 866
      resp.numBeats shouldBe 433
      resp.numSections shouldBe 10
      resp.numSegments shouldBe 755

      resp.averageBarLength should be(1.841694166666667 +- 0.01)
      resp.averageBarConfidence should be(0.4981574074074074 +- 1.0)
      resp.bars.head.getDuration shouldBe a[Duration]
    }
  }
}
