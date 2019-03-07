package io.scarman.spotify
import io.scarman.spotify.auth.ClientCredentials
import io.scarman.spotify.request.UnitSpec
import io.scarman.spotify.response.AccessToken
import io.scarman.spotify.request.PlatformSpec._

class TokenSpec extends UnitSpec {

  describe("Test getting auth tokens") {

    it("Should get a valid token...") {
      auth.isExpired.map(_ shouldBe false)
      auth.getToken.map(_.access_token should not be empty)
    }

    it("Should refresh the token...") {
      val t = auth.getToken

      t.flatMap { tok =>
        val bumToken = AccessToken.expired
        auth.refreshToken()
      }

      auth.isExpired.map(_ shouldBe false)
      auth.getToken.map(_.access_token should not be empty)
    }
  }
}
