A Scala library for the Spotify API. The documentation on the Spotify API can be found [here](https://developer.spotify.com/web-api/). It currently
supports most features surrounding artists, tracks, and albums. Currently no user support is implemented. 

#####Adding to project
`libraryDependencies += "io.scarman" %% "spotify-api" % "0.1"`

#####Using the Library
There are 2 ways to make use of this library and there are some important things to keep in mind when using this library.
The packages of this library are separated into request and response. Not surprisingly these are for the request portion
and response portion of the API. It's important to understand this so you use the right classes.

#####First use
```scala
import scala.concurrent.Future
import io.scarman.spotify._
import io.scarman.spotify.{response => resp}

val appId = ""
val appSecret = ""

val spotify = Spotify(appId, appSecret)

// These are the same thing.
val artistId = ""
val artist: Artist = spotify.getArtist(artistId)
val artist2: Artist = Artist(artistId)

val response: Future[resp.Artist] = artist()
```

In this example, we create the initial entrance point via `Spotify` and then create a request for an Artist. The
response from this request would be the serialized result of the Future. If you've used dispatch before this should look
familiar to you as the request isn't actually made until the `.apply()`  method is called on the request object. It's the
users responsibility to deal with the future at this point, but some of the response objects have methods on them which
make subsequent requests using information you've already provided much easier.

```scala
import io.scarman.spotify._
val artist = Artist("id")
val albums = artist.albums()
```