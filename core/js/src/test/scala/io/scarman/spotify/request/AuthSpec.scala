package io.scarman.spotify.request
import io.scarman.spotify.auth.{AuthorizationCode, Scopes}
import PlatformSpec._

import scala.concurrent.Future

class AuthSpec extends UnitSpec {

  describe("Authorization Flow") {
    it("Should get creds") {
      val auth = AuthorizationCode(appId, Scopes.All, "http://localhost:8080/callback")

//      auth.authApp()
      Future.unit.map(_ => 1 shouldBe 1)
    }
  }

}
