package io.scarman.spotify.auth

object Scopes {
  object Playlists {
    val ReadPrivate       = "playlist-read-private"
    val ModifyPrivate     = "playlist-modify-private"
    val ModifyPublic      = "playlist-modify-public"
    val ReadCollaborative = "playlist-read-collaborative"
    val All               = s"$ReadPrivate $ReadCollaborative $ModifyPublic $ModifyPrivate"
  }
  object Connect {
    val ModifyPlaybackState  = "user-modify-playback-state"
    val ReadCurrentlyPlaying = "user-read-currently-playing"
    val ReadPlaybackState    = "user-read-playback-state"
    val All                  = s"$ModifyPlaybackState $ReadCurrentlyPlaying $ReadPlaybackState"
  }
  object History {
    val TopRead            = "user-top-read"
    val ReadRecentlyPlayed = "user-read-recently-played"
    val All                = s"$TopRead $ReadRecentlyPlayed"
  }
  object Playback {
    val RemoteControl = "app-remote-control"
    val Streaming     = "streaming"
    val All           = s"$RemoteControl $Streaming"
  }
  object Users {
    val ReadBirthdate = "user-read-birthdate"
    val ReadEmail     = "user-read-email"
    val ReadPrivate   = "user-read-private"
    val All           = s"$ReadBirthdate $ReadEmail $ReadPrivate"
  }
  object Follow {
    val FollowRead   = "user-follow-read"
    val FollowModify = "user-follow-modify"
    val All          = s"$FollowModify $FollowRead"
  }
  object Library {
    val LibraryModify = "user-library-modify"
    val LibraryRead   = "user-library-read"
    val All           = s"$LibraryModify $LibraryRead"
  }
  val All = s"${Playlists.All} ${Connect.All} ${History.All} ${Playback.All} ${Users.All} ${Follow.All} ${Library.All}"
}
