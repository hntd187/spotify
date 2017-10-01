package io.scarman.spotify.http

import scala.concurrent.ExecutionContext.Implicits.global

import dispatch._

import io.scarman.spotify.response.Paging

private[spotify] trait PagingRequest[R <: Paging[_]] extends LastResponse[R] { http: HttpRequest[R] =>
  protected var pageNumber: Int = 1

  def getPageNumber: Int = pageNumber

  def hasNext: Boolean = {
    lastResponse match {
      case Some(p) => p.next.nonEmpty
      case None    => false
    }
  }

  def hasPrevious: Boolean = {
    lastResponse match {
      case Some(p) => p.previous.nonEmpty
      case None    => false
    }
  }

  private def getPage(page: Option[String]): Future[Option[R]] = {
    page match {
      case Some(u) => get(url(u)).map(Some(_))
      case None    => Future.successful(None)
    }
  }

  def nextPage(): Future[Option[R]] = {
    lastResponse match {
      case Some(p) =>
        pageNumber += 1
        getPage(p.next)
      case None => Future.successful(None)
    }
  }

  def previousPage(): Future[Option[R]] = {
    lastResponse match {
      case Some(p) =>
        pageNumber -= 1
        getPage(p.previous)
      case None => Future.successful(None)
    }
  }

}
