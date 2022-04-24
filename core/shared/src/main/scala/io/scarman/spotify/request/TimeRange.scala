package io.scarman.spotify.request

enum TimeRange(val name: String) {

  /** Several years of data and including all new data as it becomes available
    */
  case LongTerm extends TimeRange("long_term")

  /** Approximately the last 6 months of data
    */
  case MediumTerm extends TimeRange("medium_term")

  /** Approximately the last 4 weeks of data
    */
  case ShortTerm extends TimeRange("short_term")
}
