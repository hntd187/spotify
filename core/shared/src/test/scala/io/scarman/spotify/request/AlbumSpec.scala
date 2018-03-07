package io.scarman.spotify.request

class AlbumSpec extends UnitSpec {

  import UnitSpec._

  describe("Tests for Album Endpoints") {
    it("Should get a sweet Pitbull album") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()

      /*
      val images = i.images.head
      val cover  = images.download(s"$testPath/cover.png")

      val coverResult = cover()
      coverResult.checksum.get shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverResult.result shouldBe Downloaded

      val coverAgain  = images.download(s"$testPath/cover.png", Some("3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"))
      val coverExists = coverAgain.futureValue
      coverExists.checksum.value shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverExists.result shouldBe AlreadyExists
       */

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
      val tracks = album.tracks(limit = 4)

      val previousPage = tracks.previousPage()

      tracks.hasPrevious shouldBe false
      previousPage.map(_.nonEmpty shouldBe false)

      val firstPage = tracks()
      firstPage.map(_.items should have length 4)

      tracks.hasNext shouldBe true

      val secondPage = tracks.nextPage().map(_.get)
      secondPage.map(_.items should have length 4)
      tracks.getPageNumber shouldBe 2

      tracks.hasNext shouldBe true
      tracks.nextPage().map(_.isEmpty shouldBe false)
      tracks.hasPrevious shouldBe true
      val previousWorks = tracks.previousPage().map(_.get)
      tracks.getPageNumber shouldBe 1
      previousWorks.map(_.items should have length 4)

    }

    it("Should get some sweet daft punk albums") {
      val albums = spotify.getAlbums("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo")
      val result = albums()

      result.map { r =>
        val artists = for {
          album  <- r.albums
          artist <- album.artists
        } yield (artist.name, album.name)

        artists.head._1 shouldBe "Daft Punk"
        artists.head._2 shouldBe "TRON: Legacy Reconfigured"
        artists.last._1 shouldBe "Daft Punk"
        artists.last._2 shouldBe "Human After All"
      }
    }
  }
}
