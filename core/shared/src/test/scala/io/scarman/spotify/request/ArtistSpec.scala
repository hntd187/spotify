package io.scarman.spotify.request

import PlatformSpec._

class ArtistSpec extends UnitSpec {

  describe("Tests for Artist Endpoints") {
    it("Should get Pitbull's sweet music") {
      val request  = spotify.getArtist(id = "0TnOYISbd1XYRBk9myaseg")
      val response = request()
      response.map { r =>
        r.name shouldBe "Pitbull"
        r.`type` shouldBe "artist"
        r.popularity.get shouldBe (89 +- 5)
      }
      val topTracks = request.topTracks()

      topTracks().map(_().head.name shouldBe "Time of Our Lives")
      topTracks().map(_().last.name shouldBe "International Love")

      val related      = request.relatedArtists()
      val relatedDudes = related()

      relatedDudes.map(_().head.name shouldBe "Flo Rida")
      relatedDudes.map(_().last.name shouldBe "Madcon")
    }

    it("Should get Pitbull's platinum albums") {
      val request = spotify.getArtist(id = "0TnOYISbd1XYRBk9myaseg").albums(fullalbum)
      val albums  = request()
//      val nextPage = request.nextPage()

      albums.map { a =>
        a.items.head.name shouldBe "Climate Change"
        a.items.last.name shouldBe "Pitbull Starring In Rebelution"
      }

      val artistAlbums = spotify.getArtistAlbums(id = "0TnOYISbd1XYRBk9myaseg", albumType = List(fullalbum))
      val aa           = artistAlbums()
      val np           = aa.flatMap(_.nextPage())
      for { f <- np } {
        f.total shouldBe 23
      }

      aa.map { a =>
        a.items.head.name shouldBe "Climate Change"
        a.items.last.name shouldBe "Pitbull Starring In Rebelution"
      }

      // How many albums does pitbull have??
      val allAlbums = spotify.getArtistAlbums(id = "0TnOYISbd1XYRBk9myaseg", limit = 50)
      val resp      = allAlbums()
      resp.map(_.items should have length 50)
    }

    it("Should get some sweet EDM artists") {
      val request  = spotify.getArtists("2CIMQHirSU0MQqyYHq0eOx", "57dN52uHvrHOxijzpIgu3E", "1vCWHaC5f2uS3yhpwWbIA6")
      val response = request()

      response.map(_().head.name shouldBe "deadmau5")
      response.map(_(1).name shouldBe "Ratatat")
      response.map(_().last.name shouldBe "deadmau5")
    }
  }
}
