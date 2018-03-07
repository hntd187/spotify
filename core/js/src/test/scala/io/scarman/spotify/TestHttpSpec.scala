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
        println(s"${r.statusCode} - ${r.body}\n ${r.headers}")
        assert(r.body == "{\"ip\":\"209.249.202.82\"}")
        assert(r.statusCode == 200)
      }
    }
  }
}
