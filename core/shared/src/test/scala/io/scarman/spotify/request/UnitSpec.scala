package io.scarman.spotify.request

import scala.concurrent.{ExecutionContext, Future}

import org.scalatest._
import org.scalatest.concurrent._
import org.scalatest.time._
import io.scarman.spotify.Spotify
import monix.execution.Scheduler

object UnitSpec {

  implicit val scheduler: Scheduler               = Scheduler.trampoline()
  implicit val executionContext: ExecutionContext = scheduler

  val appId: String     = "2a40953e7c854dd48d257a10ad6de863"
  val appSecret: String = "00115daaef1a4e209c87c8805643c7d2"
  val spotify: Spotify  = Spotify(appId, appSecret)

  val sweet_pitbull_album: String = "4aawyAB9vmqN3uQ7FjRGTy"
  val trackId: String             = "1zHlj4dQ8ZAtrayhuDDmkY"
}

trait UnitSpec extends AsyncFunSpec with Matchers with OptionValues with BeforeAndAfterAll with ScalaFutures {

  implicit override val executionContext: ExecutionContext = UnitSpec.scheduler
  implicit val defaultPatience: PatienceConfig             = PatienceConfig(timeout = Span(5, Seconds), interval = Span(1, Second))

  def await[T](f: Future[T]): T = f.futureValue

  val testPath: String = System.getProperty("java.io.tmpdir")
  //new File(s"$testPath/cover.png").deleteOnExit()
}
