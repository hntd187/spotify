package io.scarman.spotify.request

sealed trait TimeRange { val name: String }

object TimeRange {

  /**
    * Several years of data and including all new data as it becomes available
    */
  case object LongTerm extends TimeRange { val name = "long_term" }

  /**
    * Approximately the last 6 months of data
    */
  case object MediumTerm extends TimeRange { val name = "medium_term" }

  /**
    * Approximately the last 4 weeks of data
    */
  case object ShortTerm extends TimeRange { val name = "short_term" }
}
