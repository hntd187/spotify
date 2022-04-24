package io.scarman.spotify.request

class CategorySpec extends UnitSpec {

  describe("Test browse categories") {
    it("should return some categories") {
      val request  = spotify.getCategories()
      val response = request()

      response.map { r =>
        // paging
        assert(r.categories.href != null)
        assert(r.categories.next != null)
        assert(r.categories.total >= r.categories.items.length)

        // category is populated
        assert(r.categories.items.head.href != null)
        assert(r.categories.items.head.id != null)
      }
    }

    it("should honor the limit params") {
      val request  = spotify.getCategories(limit = 1)
      val response = request()

      response.map { r =>
        assert(r.categories.items.length == 1)
      }
    }
  }

  describe("Test browse individual category") {
    it("should return the hiphop category") {
      val request  = spotify.getCategory("hiphop")
      val response = request()

      response.map { r =>
        assert(r.href == "https://api.spotify.com/v1/browse/categories/hiphop")
        assert(r.id == "hiphop")
      }
    }
  }

  describe("Test browse a given category's playlists") {
    it("should return a list of hiphop playlists") {
      val request  = spotify.getCategoryPlaylists("hiphop")
      val response = request()

      response.map { r =>
        // paging
        assert(r.playlists.href != null)
        assert(r.playlists.total >= r.playlists.items.length)
        assert(r.playlists.items.head.`type` == "playlist")
      }
    }
  }
}
