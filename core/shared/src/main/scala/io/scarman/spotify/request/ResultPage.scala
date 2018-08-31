package io.scarman.spotify.request

import scala.concurrent.Future

import fr.hmil.roshttp.{HttpRequest => HR}
import io.circe.Decoder
import monix.execution.Scheduler

import io.scarman.spotify.Spotify
import io.scarman.spotify.http._
import io.scarman.spotify.response.Paging

case class ResultPage[R <: Paging[R]](current: Future[R], page: Int = 1)(implicit spotify: Spotify,
                                                                         val scheduler: Scheduler,
                                                                         decoder: Decoder[R])
    extends HttpRequest[R] {

  protected val request = HR()

  def items(): Future[List[R]] = current.map(_.items)

  def nextPage(): Future[ResultPage[R]] =
    current.map {
      case Paging(_, _, _, Some(n), _, _, _) => ResultPage[R](get(HR(n)), page - 1)
    }

  def previousPage(): Future[ResultPage[R]] = {
    current.map {
      case Paging(_, _, _, _, _, Some(n), _) => ResultPage[R](get(HR(n)), page - 1)
    }
  }

  def hasNext: Future[Boolean] =
    for {
      cur <- current
    } yield cur.next.nonEmpty

  def hasPrevious: Future[Boolean] =
    for {
      cur <- current
    } yield cur.previous.nonEmpty

}
