package io.scarman.spotify.response

case class User(
    display_name: String,
    external_urls: List[ExternalUrl],
    followers: Followers,
    href: String,
    id: String,
    images: List[Image],
    `type`: String,
    uri: String
)
