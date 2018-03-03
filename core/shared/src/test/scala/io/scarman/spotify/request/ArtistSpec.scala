package io.scarman.spotify.request

class ArtistSpec extends UnitSpec {

  import UnitSpec._

  describe("Tests for Artist Endpoints") {
    it("Should get Pitbull's sweet music") {
      val request  = spotify.getArtist(id = "0TnOYISbd1XYRBk9myaseg")
      val response = await(request())

      response.name shouldBe "Pitbull"
      response.`type` shouldBe "artist"
      response.popularity.get shouldBe (89 +- 5)
      val topTracks = await(request.topTracks().apply())

      topTracks().head.name shouldBe "Time of Our Lives"
      topTracks().last.name shouldBe "International Love"

      val related      = request.relatedArtists()
      val relatedDudes = await(related())

      relatedDudes().head.name shouldBe "Flo Rida"
      relatedDudes().last.name shouldBe "The Black Eyed Peas"
    }

    it("Should get Pitbull's platinum albums") {
      val request  = spotify.getArtist(id = "0TnOYISbd1XYRBk9myaseg").albums(fullalbum)
      val albums   = await(request())
      val nextPage = await(request.nextPage())

      albums.items.head.name shouldBe "Climate Change"
      albums.items.last.name shouldBe "Pitbull Starring In Rebelution"

      val artistAlbums = spotify.getArtistAlbums(id = "0TnOYISbd1XYRBk9myaseg", albumType = List(fullalbum))
      val aa           = await(artistAlbums())
      val np           = await(artistAlbums.nextPage())

      aa.items.head.name shouldBe "Climate Change"
      aa.items.last.name shouldBe "Pitbull Starring In Rebelution"

      // How many albums does pitbull have??
      val allAlbums = spotify.getArtistAlbums(id = "0TnOYISbd1XYRBk9myaseg", limit = 50)
      val resp      = await(allAlbums())
      resp.items should have length 50
    }

    it("Should get some sweet EDM artists") {
      val request  = spotify.getArtists("2CIMQHirSU0MQqyYHq0eOx", "57dN52uHvrHOxijzpIgu3E", "1vCWHaC5f2uS3yhpwWbIA6")
      val response = await(request())

      response().head.name shouldBe "deadmau5"
      response(1).name shouldBe "Ratatat"
      response().last.name shouldBe "Avicii"
    }
  }
}