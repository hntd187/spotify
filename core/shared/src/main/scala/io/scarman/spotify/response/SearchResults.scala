package io.scarman.spotify.response

import io.scarman.spotify.request.{AlbumPage, ArtistPage, PlaylistPage, TrackPage}

case class SearchResults(
    tracks: Option[TrackPage],
    artists: Option[ArtistPage],
    albums: Option[AlbumPage],
    playlist: Option[PlaylistPage]
)
