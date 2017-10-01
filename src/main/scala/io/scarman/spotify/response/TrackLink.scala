package io.scarman.spotify.response

case class TrackLink(external_urls: Seq[ExternalUrl],
                     href: String,
                     id: String,
                     `type`: String,
                     uri: String)
