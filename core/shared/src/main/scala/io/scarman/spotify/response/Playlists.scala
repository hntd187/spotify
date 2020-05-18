package io.scarman.spotify.response

case class Playlists(
    playlists: Paging[Playlist]
)
