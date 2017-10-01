package io.scarman.spotify.response

case class Paging[T](href: String, items: List[T], limit: Int, next: Option[String], offset: Int, previous: Option[String], total: Int)
