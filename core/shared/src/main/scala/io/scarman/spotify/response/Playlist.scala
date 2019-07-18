package io.scarman.spotify.response

case class Playlist(collaborative: Boolean,
                    external_urls: List[ExternalUrl],
                    href: String,
                    id: String,
                    images: List[Image],
                    name: String,
                    owner: User,
                    public: Option[Boolean],
                    snapshot_id: String,
                    tracks: List[Track],
                    `type`: String,
                    uri: String)
