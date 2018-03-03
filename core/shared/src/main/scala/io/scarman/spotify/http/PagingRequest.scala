package io.scarman.spotify.http

import scala.concurrent.Future

import fr.hmil.roshttp.{HttpRequest => HR}
import monix.execution.Scheduler.Implicits.global
import monix.execution.atomic.AtomicInt

import io.scarman.spotify.response.Paging

private[spotify] trait PagingRequest[R <: Paging[_]] extends LastResponse[R] { http: HttpRequest[R] =>

  protected var pageNumber: AtomicInt = AtomicInt(1)

  def getPageNumber: Int = pageNumber.get

  def hasNext: Boolean = {
    logger.info(s"Page Number: ${pageNumber.get}")
    synchronized {
      lastResponse.toOption.exists(_.next.nonEmpty)
    }
  }

  def hasPrevious: Boolean = {
    logger.info(s"Page Number: ${pageNumber.get}")
    if (pageNumber.get == 1) return false
    synchronized {
      lastResponse.toOption.exists(_.previous.nonEmpty)
    }
  }

  private def getPage(page: String): Future[Option[R]] = get(HR(page)).map(Some(_))

  def nextPage(): Future[Option[R]] = {
    lastResponse.toOption.map { r =>
      r.next match {
        case Some(s) =>
          pageNumber.increment()
          getPage(s)
        case _ => Future.successful(None)
      }
    }.getOrElse(Future.successful(None))
  }

  def previousPage(): Future[Option[R]] = {

    lastResponse.toOption.map { r =>
      pageNumber.decrement()
      r.previous match {
        case Some(s) =>
          pageNumber.decrement()
          getPage(s)
        case _ => Future.successful(None)
      }
    }.getOrElse(Future.successful(None))
  }

}
