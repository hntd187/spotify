package io.scarman.spotify.response

import io.scarman.spotify.util.SongDuration

case class Track(artists: List[Artist],
                 disc_number: Int,
                 duration_ms: Long,
                 explicit: Boolean,
                 external_urls: Option[ExternalUrl],
                 href: String,
                 id: String,
                 name: String,
                 preview_url: Option[String],
                 track_number: Int,
                 tpe: String,
                 uri: String,
                 is_playable: Option[Boolean],
                 linked_from: Option[TrackLink],
                 album: Option[SimpleAlbum],
                 popularity: Option[Int],
                 external_ids: Option[ExternalId])
    extends SongDuration
