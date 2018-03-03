package io.scarman.spotify.request

import scala.concurrent.duration.Duration

import io.scarman.spotify.response.SimpleAlbum

class TrackSpec extends UnitSpec {

  import UnitSpec._

  describe("Tests for Track Endpoints") {

    it("Should get a sweet Pitbull track") {

      val track   = spotify.getTrack(trackId)
      val result  = track().futureValue
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

    it("Should get multiple hot fire tracks from Pitbull") {
      val tracks = spotify.getTracks("6OmhkSOpvYBokMKQxpIGx2", "1zHlj4dQ8ZAtrayhuDDmkY")
      val result = await(tracks())

      result.tracks should have length 2
      result.tracks.head.name shouldBe "Global Warming"
      result.tracks.last.name shouldBe "Timber"
    }
  }
}
