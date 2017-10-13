package io.scarman.spotify.request

import java.io.File

import io.scarman.spotify.http.{AlreadyExists, Downloaded}

class AlbumSpec extends UnitSpec {

  import UnitSpec._

  new File(s"$testPath/cover.png").deleteOnExit()

  describe("Tests for Album Endpoints") {
    it("Should get a sweet Pitbull album") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = await(album())
      val images = result.images.head
      val cover  = images.download(s"$testPath/cover.png")

      val coverResult = await(cover)
      coverResult.checksum.get shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverResult.result shouldBe Downloaded

      val coverAgain  = images.download(s"$testPath/cover.png", Some("3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"))
      val coverExists = await(coverAgain)
      coverExists.checksum.value shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverExists.result shouldBe AlreadyExists

      result.name shouldBe "Global Warming"
      result.artists.head.name shouldBe "Pitbull"

      val tracks       = album.tracks(limit = 50)
      val trackResults = await(tracks())

      trackResults.items should have length 18

      val albumTracks = spotify.getAlbumTracks(id = sweet_pitbull_album, limit = 50)
      val results     = await(albumTracks())
      results.items should have length 18
    }

    it("Should page tracks properly") {
      val album  = spotify.getAlbum(sweet_pitbull_album)
      val tracks = album.tracks(limit = 9)

      val previousPage = await(tracks.previousPage())
      tracks.hasPrevious shouldBe false
      previousPage.isEmpty shouldBe true

      val firstPage = await(tracks())
      firstPage.items should have length 9

      tracks.hasNext shouldBe true

      val secondPage = await(tracks.nextPage()).get
      secondPage.items should have length 9
      tracks.getPageNumber shouldBe 2

      tracks.hasNext shouldBe false
      await(tracks.nextPage()).isEmpty shouldBe true
      tracks.hasPrevious shouldBe true
      val previousWorks = await(tracks.previousPage()).get
      tracks.getPageNumber shouldBe 1
      previousWorks.items should have length 9

    }

    it("Should get some sweet daft punk albums") {
      val albums = spotify.getAlbums("382ObEPsp2rxGrnsizN5TX", "1A2GTWGtFfWp7KSQTwWOyo")
      val result = await(albums())

      val artists = for {
        album  <- result.albums
        artist <- album.artists
      } yield (artist.name, album.name)

      artists.head._1 shouldBe "Daft Punk"
      artists.head._2 shouldBe "TRON: Legacy Reconfigured"

      artists.last._1 shouldBe "Daft Punk"
      artists.last._2 shouldBe "Human After All"
    }
  }
}
