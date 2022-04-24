package io.scarman.spotify.response

import io.scarman.spotify.request.TrackPage

case class Album(
    album_type: String,
    artists: List[Artist],
    available_markets: Option[List[String]],
    copyrights: List[Copyright],
    external_ids: ExternalId,
    external_urls: ExternalUrl,
    genres: Seq[String],
    href: String,
    id: String,
    images: List[Image],
    label: String,
    name: String,
    popularity: Int,
    release_date: String,
    release_date_precision: String,
    linked_from: Option[ExternalUrl],
    tracks: TrackPage,
    `type`: String,
    uri: String
)

case class SimpleAlbum(
    album_type: String,
    artists: List[Artist],
    external_urls: ExternalUrl,
    href: String,
    id: String,
    images: List[Image],
    name: String,
    `type`: String,
    uri: String
)
