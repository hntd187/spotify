package io.scarman.spotify.request

import io.scarman.spotify.http._
import io.scarman.spotify.response.Image
import io.scarman.spotify.request.PlatformSpec._

class ImageSpec extends UnitSpec {

  describe("Image Spec") {
    it("Should download an image") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()
      val image  = result.flatMap(_.images.head.asInstanceOf[Image].download(s"$testPath/cover.jpg"))

      image.map(_.checksum.get shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced")
      image.map(_.result shouldBe Downloaded)

    }
  }

}
