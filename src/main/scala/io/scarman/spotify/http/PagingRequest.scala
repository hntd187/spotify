package io.scarman.spotify.http

import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._

import io.scarman.spotify.response.Paging

private[spotify] trait PagingRequest[R <: Paging[_]] extends LastResponse[R] { http: HttpRequest[R] =>
  protected var pageNumber: Int = 1

  def getPageNumber: Int = pageNumber

  def hasNext: Boolean = {
    lastResponse.exists(_.next.nonEmpty)
  }

  def hasPrevious: Boolean = {
    lastResponse.exists(_.previous.nonEmpty)
  }

  private def getPage(page: String): Future[Option[R]] = {
    get(url(page)).map(Some(_))
  }

  def nextPage(): Future[Option[R]] = {
    lastResponse match {
      case Some(p) =>
        p.next.map { p =>
          pageNumber += 1
          getPage(p)
        }.getOrElse {
          Future.successful(None)
        }
      case None => Future.successful(None)
    }
  }

  def previousPage(): Future[Option[R]] = {
    lastResponse match {
      case Some(p) =>
        pageNumber -= 1
        p.previous.map(getPage).getOrElse(Future.successful(None))
      case None => Future.successful(None)
    }
  }

}
