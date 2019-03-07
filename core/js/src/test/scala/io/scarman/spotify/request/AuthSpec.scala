package io.scarman.spotify.request
import io.scarman.spotify.auth.{AuthorizationCode, Scopes}
import PlatformSpec._

import scala.concurrent.Future

class AuthSpec extends UnitSpec {

  describe("Authorization Flow") {
    it("Should get creds") {
      val auth = AuthorizationCode(appId, Scopes.All, "localhost:8080/callback")
      Future.unit.map(_ => 1 shouldBe 1)
    }
  }

}
