package io.scarman.spotify.response

case class Playlist(collaborative: Boolean,
                    external_urls: ExternalUrl,
                    href: String,
                    id: String,
                    images: List[SharedImage],
                    name: String,
                    owner: User,
                    public: Option[Boolean],
                    snapshot_id: String,
                    tracks: TracksRef,
                    `type`: String,
                    uri: String)
