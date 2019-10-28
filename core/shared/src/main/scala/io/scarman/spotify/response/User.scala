package io.scarman.spotify.response

/**
  * Note the Spotify "user" leaves out a few fields, thus the Option
  */
case class User(
    display_name: String,
    external_urls: ExternalUrl,
    followers: Option[Followers],
    href: String,
    id: String,
    images: Option[List[SharedImage]],
    `type`: String,
    uri: String
)
