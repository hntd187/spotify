package io.scarman.spotify

import scalajs.js.Date

import com.softwaremill.sttp.FetchBackend

object Platform {

  implicit val backend = FetchBackend()

  def after(d: Long): Boolean = {
    val dd = new Date()
    dd.setTime(Date.now())
    dd.getMilliseconds() > d
  }

}
