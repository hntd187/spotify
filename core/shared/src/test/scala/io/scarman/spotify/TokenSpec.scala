package io.scarman.spotify

import scala.concurrent.Future

import io.scarman.spotify.request.UnitSpec
import io.scarman.spotify.response.AccessToken

class TokenSpec extends UnitSpec {

  import UnitSpec._

  describe("Test getting auth tokens") {

    it("Should get a valid token...") {
      spotify.isExpired.futureValue shouldBe false
      spotify.getToken.futureValue should not be empty
    }
    it("Should refresh the token...") {
      val t        = spotify.getToken.futureValue
      val bumToken = AccessToken(t, "", -1, None, None)
      spotify.refreshToken(appId, appSecret, Future.successful(bumToken))

      spotify.isExpired.futureValue shouldBe false
      spotify.getToken.futureValue should not be empty
    }
  }
}
