package io.scarman.spotify.response

import com.softwaremill.sttp._
import io.circe.Decoder
import io.scarman.spotify.Spotify
import io.scarman.spotify.request.RequestPage

import scala.concurrent.{ExecutionContext, Future}

case class Paging[T](href: String, items: List[T], limit: Int, next: Option[String], offset: Int, previous: Option[String], total: Int) {

  private def page_req(u: Option[String])(implicit s: Spotify,
                                          b: SttpBackend[Future, Nothing],
                                          d: Decoder[Paging[T]],
                                          execution: ExecutionContext): Option[Future[Paging[T]]] = {
    for {
      uri <- u
      rp  = RequestPage(uri"$uri")
    } yield rp()
  }

  def hasNext: Boolean = next.isDefined

  def hasPrev: Boolean = previous.isDefined

  def nextPage()(implicit s: Spotify, d: Decoder[Paging[T]]): Future[Paging[T]] = {
    page_req(next)(s, s.backend, d, s.execution) match {
      case Some(p) => p
      case None    => throw new Exception("Called next page when no page is available.")
    }
  }

  def previousPage()(implicit s: Spotify, d: Decoder[Paging[T]]): Future[Paging[T]] = {
    page_req(previous)(s, s.backend, d, s.execution) match {
      case Some(p) => p
      case None    => throw new Exception("Called previous page when no page is available.")
    }
  }
}
