package io.scarman.spotify.request

class SearchSpec extends UnitSpec {

  import UnitSpec._

  describe("Searching") {
    it("Should return results") {
      val s = spotify.search("artist:pitbull", "album")

      s().map { sr =>
        sr.albums.value.items should have length 20
      }
    }
  }

}
