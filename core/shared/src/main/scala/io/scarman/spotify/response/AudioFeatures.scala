package io.scarman.spotify.response

import io.scarman.spotify.util.SongDuration

case class AudioFeatures(
    danceability: Double,
    energy: Double,
    key: Int,
    loudness: Double,
    mode: Int,
    speechiness: Double,
    acousticness: Double,
    instrumentalness: Double,
    liveness: Double,
    valence: Double,
    tempo: Double,
    `type`: String,
    id: String,
    uri: String,
    track_href: String,
    analysis_url: String,
    duration_ms: Long,
    time_signature: Int
) extends SongDuration
