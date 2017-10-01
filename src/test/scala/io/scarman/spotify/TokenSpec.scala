package io.scarman.spotify

import io.scarman.spotify.request.UnitSpec
import io.scarman.spotify.response.AccessToken

class TokenSpec extends UnitSpec {

  import UnitSpec._

  describe("Test getting auth tokens") {
    it("Should get a valid token...") {
      spotify.isExpired shouldBe false
      spotify.getToken should not be empty
    }
    it("Should refresh the token...") {
      val token    = spotify.getToken
      val bumToken = AccessToken(token, "", -1, None, None)
      spotify.refreshToken(appId, appSecret, bumToken)
      spotify.isExpired shouldBe false
      spotify.getToken should not be empty
    }
  }
}
