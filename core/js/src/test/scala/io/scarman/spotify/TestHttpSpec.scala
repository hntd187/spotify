package io.scarman.spotify

import fr.hmil.roshttp.{HttpRequest, Method, Protocol}
import io.scarman.spotify.request.UnitSpec

class TestHttpSpec extends UnitSpec {

  import UnitSpec._

  describe("Test") {

    it("Should work?") {

      val req = HttpRequest()
        .withProtocol(Protocol.HTTPS)
        .withHost("api.ipify.org")
        .withPath("/")
        .withMethod(Method.GET)
        .withQueryParameter("format", "json")

      val sent = req.send()
      sent.map { r =>
        assert(r.body == "{\"ip\":\"73.13.171.142\"}")
        assert(r.statusCode == 200)
      }
    }
  }
}
