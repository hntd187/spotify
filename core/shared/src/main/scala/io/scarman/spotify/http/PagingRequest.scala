package io.scarman.spotify.http

import scala.concurrent.Future

import fr.hmil.roshttp.{HttpRequest => HR}
import monix.execution.atomic.AtomicInt
import io.scarman.spotify.response.Paging
import monix.execution.Scheduler

private[spotify] trait PagingRequest[R <: Paging[_]] extends LastResponse[R] { http: HttpRequest[R] =>

  protected implicit val scheduler: Scheduler
  protected var pageNumber: AtomicInt = AtomicInt(1)

  def getPageNumber: Int = pageNumber.get

  def hasNext: Boolean = {
    synchronized {
      lastResponse.toOption.exists(_.next.nonEmpty)
    }
  }

  def hasPrevious: Boolean = {
    if (pageNumber() == 1) return false
    synchronized {
      lastResponse.toOption.exists(_.previous.nonEmpty)
    }
  }

  private def getPage(page: String): Future[Option[R]] = {
    logger.info(s"Current Page: ${pageNumber()}")
    get(HR(page)).map(Some(_))
  }

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
