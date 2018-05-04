package io.scarman.spotify.http

import scala.concurrent.Future

import io.scarman.spotify.request.ResultPage

trait PagingRequest[R <: ResultPage[_]] extends HttpRequest[R] {

  protected var page: Int
  protected var current: Future[R]

  def pageNumber: Int = page

  def nextPage(): Future[R]

  def previousPage(): Future[R]

  def hasNext: Future[Boolean]

  def hasPrevious: Future[Boolean]

}
