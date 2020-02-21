package io.scarman.spotify.request

import io.scarman.spotify.Spotify
import io.scarman.spotify.auth.ClientCredentials
import org.scalatest._
import org.scalatest.concurrent._
import org.scalatest.time._
import io.scarman.spotify.request.PlatformSpec._

import scala.concurrent.ExecutionContext
import org.scalatest.funspec.AsyncFunSpec
import org.scalatest.matchers.should.Matchers

object UnitSpec {}

trait UnitSpec extends AsyncFunSpec with Matchers with OptionValues with BeforeAndAfterAll with ScalaFutures {

  import PlatformSpec._

  implicit override def executionContext: ExecutionContext = ExecutionContext.Implicits.global

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(1, Second))

  val appId: String     = "2a40953e7c854dd48d257a10ad6de863"
  val appSecret: String = "00115daaef1a4e209c87c8805643c7d2"

  implicit val auth: ClientCredentials = ClientCredentials(appId, appSecret)
  implicit val spotify: Spotify        = Spotify(auth)

  val sweet_pitbull_album: String = "4aawyAB9vmqN3uQ7FjRGTy"
  val trackId: String             = "1zHlj4dQ8ZAtrayhuDDmkY"

  val testPath: String = System.getProperty("java.io.tmpdir")
}
