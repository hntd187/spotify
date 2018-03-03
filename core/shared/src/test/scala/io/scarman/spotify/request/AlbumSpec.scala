package io.scarman.spotify.request

class AlbumSpec extends UnitSpec {

  import UnitSpec._

  describe("Tests for Album Endpoints") {
    it("Should get a sweet Pitbull album") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()

      val i = await(result)
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
      i.name shouldBe "Global Warming"
      i.artists.head.name shouldBe "Pitbull"

      val tracks       = album.tracks(limit = 50)
      val trackResults = tracks()

      trackResults.futureValue.items should have length 18

      val albumTracks = spotify.getAlbumTracks(id = sweet_pitbull_album, limit = 50)
      val results     = await(albumTracks())
      results.items should have length 18
    }

    it("Should page tracks properly") {
      val album  = spotify.getAlbum(sweet_pitbull_album)
      val tracks = album.tracks(limit = 4)

      val previousPage = tracks.previousPage()

      tracks.hasPrevious shouldBe false
      previousPage.futureValue.nonEmpty shouldBe false

      val firstPage = tracks()
      firstPage.futureValue.items should have length 4

      tracks.hasNext shouldBe true

      val secondPage = tracks.nextPage().futureValue.get
      secondPage.items should have length 4
      tracks.getPageNumber shouldBe 2

      tracks.hasNext shouldBe true
      tracks.nextPage().futureValue.isEmpty shouldBe false
      tracks.hasPrevious shouldBe true
      val previousWorks = tracks.previousPage().futureValue.get
      tracks.getPageNumber shouldBe 1
      previousWorks.items should have length 4

    }

    it("Should get some sweet daft punk albums") {
      val albums = spotify.getAlbums("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo")
      val result = await(albums())

      val artists = for {
        album  <- result.albums
        artist <- album.artists
      } yield (artist.name, album.name)

      println("Hello world!")

      artists.head._1 shouldBe "Daft Punk"
      artists.head._2 shouldBe "TRON: Legacy Reconfigured"
      artists.last._1 shouldBe "Daft Punk"
      artists.last._2 shouldBe "Human After All"
    }
  }
}
