package io.scarman.spotify.response

case class Artist(external_urls: ExternalUrl,
                  href: String,
                  id: String,
                  name: String,
                  tpe: String,
                  uri: String,
                  popularity: Option[Int],
                  genres: Option[List[String]],
                  followers: Option[Followers],
                  images: Option[List[Image]])
