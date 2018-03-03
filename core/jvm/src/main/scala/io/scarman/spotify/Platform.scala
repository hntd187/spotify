package io.scarman.spotify

import java.time._

object Platform {

  def after(d: Long): Boolean = {
    val start = Instant.ofEpochMilli(d).atZone(ZoneId.systemDefault()).toLocalDate
    LocalDate.now().isAfter(start)
  }

}
