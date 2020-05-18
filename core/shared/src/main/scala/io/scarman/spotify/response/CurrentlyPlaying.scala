package io.scarman.spotify.response

case class CurrentlyPlaying(context: Option[Context],
                            timestamp: Long,
                            progress_ms: Long,
                            is_playing: Boolean,
                            currently_playing_type: String,
                            item: Track)
