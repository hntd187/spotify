package io.scarman.spotify.request

class SearchSpec extends UnitSpec {

  describe("Searching") {
    it("Should return results") {
      val s = spotify.search("artist:pitbull", "album")

      s().map { sr =>
        println(sr.albums.value.items.length)
        sr.albums.value.items should have length 10
      }
    }
  }

}
