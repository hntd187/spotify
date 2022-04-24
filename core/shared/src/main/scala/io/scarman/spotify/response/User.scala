package io.scarman.spotify.response

/** Note the Spotify "user" leaves out a few fields, thus the Option
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

case class ExplicitContent(filter_enabled: Boolean, filter_locked: Boolean)

case class PrivateUser(
    birthdate: Option[String],
    country: String,
    explicit_content: ExplicitContent,
    product: String,
    display_name: String,
    external_urls: ExternalUrl,
    followers: Option[Followers],
    href: String,
    id: String,
    images: Option[List[SharedImage]],
    `type`: String,
    uri: String
)
