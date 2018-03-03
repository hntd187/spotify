package io.scarman.spotify

import scalajs.js.Date

object Platform {

  def after(d: Long): Boolean = {
    val dd = new Date()
    dd.setTime(Date.now())
    dd.getMilliseconds() > d
  }

}
