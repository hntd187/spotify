package io.scarman.spotify.request

import PlatformSpec._

class AlbumSpec extends UnitSpec {

  describe("Tests for Album Endpoints") {
    it("Should get a sweet Pitbull album") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()

      result.map { i =>
        i.name shouldBe "Global Warming"
        i.artists.head.name shouldBe "Pitbull"
      }

      val tracks       = album.tracks(limit = 50)
      val trackResults = tracks()

      trackResults.map(_.items should have length 18)

      val albumTracks = spotify.getAlbumTracks(id = sweet_pitbull_album, limit = 50)
      val results     = albumTracks()
      results.map(_.items should have length 18)
    }

    it("Should page tracks properly") {
      val album  = spotify.getAlbum(sweet_pitbull_album)
      val tracks = album.tracks(limit = 6)

      tracks().map { t =>
        t.limit shouldBe 4
        t.hasPrev shouldBe false
        t.items shouldBe length(4)
      }

      recoverToSucceededIf[Exception] {
        tracks().flatMap(_.previousPage())
      }

      // Second Page
      val secondPage = tracks().flatMap(_.nextPage())
      secondPage.map { s =>
        s.items should have length 6
        s.hasNext shouldBe true
      }

      val thirdPage = secondPage.flatMap(_.nextPage())
      thirdPage.map { t =>
        t.hasNext shouldBe false
        t.hasPrev shouldBe true
      }

      recoverToSucceededIf[Exception] {
        thirdPage.flatMap(_.nextPage())
      }

      val secondPageAgain = thirdPage.flatMap(_.previousPage())
      secondPageAgain.map { sta =>
        sta.hasNext shouldBe true
        sta.hasPrev shouldBe true
      }
    }

    it("Should error on a bad ID") {
      val a = Album("asdf1234")
      recoverToSucceededIf[Exception](a())
    }

    it("Should get some sweet daft punk albums") {
      val albums = spotify.getAlbums("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo")
      val result = albums()

      result.map { r =>
        r(0).artists.head.name shouldBe "Daft Punk"
        r().head.name shouldBe "TRON: Legacy Reconfigured"
        r(1).artists.head.name shouldBe "Daft Punk"
        r().last.name shouldBe "Human After All"
      }
    }
  }
}
