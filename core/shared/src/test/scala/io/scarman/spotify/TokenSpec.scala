package io.scarman.spotify

import io.scarman.spotify.request.UnitSpec
import io.scarman.spotify.response.AccessToken

import scala.concurrent.Future

class TokenSpec extends UnitSpec {

  import UnitSpec._

  describe("Test getting auth tokens") {

    it("Should get a valid token...") {
      spotify.isExpired.map(_ shouldBe false)
      spotify.getToken.map(_ should not be empty)
    }
    it("Should refresh the token...") {
      val t = spotify.getToken

      t.flatMap { tok =>
        val bumToken = AccessToken.expired
        spotify.refreshToken(appId, appSecret, Future.successful(bumToken))
      }

      spotify.isExpired.map(_ shouldBe false)
      spotify.getToken.map(_ should not be empty)
    }
  }
}
