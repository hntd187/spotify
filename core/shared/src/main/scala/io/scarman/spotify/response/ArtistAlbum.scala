package io.scarman.spotify.response

case class ArtistAlbum(album_type: String,
                       artists: List[Artist],
                       available_markets: List[String],
                       href: String,
                       id: String,
                       images: List[Image],
                       name: String,
                       `type`: String,
                       uri: String)
