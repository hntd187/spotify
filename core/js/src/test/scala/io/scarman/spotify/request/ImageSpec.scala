package io.scarman.spotify.request

import io.scarman.spotify.http.*
import io.scarman.spotify.request.PlatformSpec.*
import io.scarman.spotify.response.Image
import scribe.Logging

class ImageSpec extends UnitSpec with Logging {

  describe("Image Spec") {
    it("Should download an image") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()

      val images = result.map(_.images.head.asInstanceOf[Image].download())

      val coverResult = images.flatMap(_.download())
      coverResult.map { cover =>
        cover.checksum.nonEmpty `shouldBe` false
        cover.result `shouldBe` Downloaded
      }
    }
  }

}
