package io.scarman.spotify.request

import io.scarman.spotify.http._
import io.scarman.spotify.request.UnitSpec._

class ImageSpec extends UnitSpec {

  describe("Image Spec") {
    it("Should download an image") {
      val album  = spotify.getAlbum(id = sweet_pitbull_album)
      val result = album()

      val i = await(result)

      val images = i.images.head
      val cover  = images.download(s"$testPath/cover.png")

      val coverResult = cover()
      coverResult.checksum.get shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverResult.result shouldBe Downloaded

      val coverAgain  = images.download(s"$testPath/cover.png", Some("3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"))
      val coverExists = coverAgain.futureValue
      coverExists.checksum.value shouldBe "3edb3f970f4a3af9ef922efd18cdb4dabaf85ced"
      coverExists.result shouldBe AlreadyExists
    }
  }

}
