package io.scarman.spotify.request

class AlbumSpec extends UnitSpec {

  import UnitSpec._

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
      val tracks = album.tracks(limit = 4).apply()

      tracks.map(_.limit shouldBe 4)

//      tracks.map(_.hasPrevious shouldBe false)
//      tracks.map(_.items().map(_.nonEmpty shouldBe false))

<<<<<<< HEAD
      // No pages
      val previousPage = tracks.previousPage()
      tracks.hasPrevious.map(_ shouldBe false)
      previousPage.map(_.nonEmpty shouldBe false)

      // First Page...
      val firstPage = tracks()
      firstPage.map(_.items should have length 4)
      tracks.hasNext.map(_ shouldBe true)

      // Second Page
      val secondPage = tracks.nextPage().map(_.get)
      secondPage.map(_.items should have length 4)
      tracks.getPageNumber.map(_ shouldBe 2)
      tracks.hasNext.map(_ shouldBe true)

      val thirdPage = tracks.nextPage()
      println(thirdPage)
      thirdPage.map(_.isEmpty shouldBe false)
      tracks.hasPrevious.map(_ shouldBe false)
//      val previousWorks = tracks.previousPage().map(_.get)
//      tracks.getPageNumber.map(_ shouldBe 1)
=======
//      tracks.map(_.items().map(_ should have length 4))

//      tracks.map(_.hasNext shouldBe true)

//      val secondPage = tracks.map(p => p.nextPage()).apply()
//      secondPage.flatMap(_.items() should have length 4)
//      tracks.getPageNumber shouldBe 2

//      tracks.map(_.hasNext shouldBe true)

//      tracks.map(_.hasPrevious.map(_ shouldBe true))
//      val previousWorks = tracks.map(_.previousPage()).apply()

//      tracks.getPageNumber shouldBe 1
>>>>>>> 2b6098bd981d27481bc4c420c249b56d111ad6f9
//      previousWorks.map(_.items should have length 4)

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
