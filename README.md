[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ee0545610a234b4ba53c58f497efb6f8)](https://app.codacy.com/app/shcarman/spotify?utm_source=github.com&utm_medium=referral&utm_content=hntd187/spotify&utm_campaign=Badge_Grade_Settings)
[ ![Download](https://api.bintray.com/packages/hntd187/maven/spotify-api/images/download.svg) ](https://bintray.com/hntd187/maven/spotify-api/_latestVersion)[![Travis](https://travis-ci.org/hntd187/spotify.svg?branch=master)](https://travis-ci.org/hntd187/spotify) [![Coverage Status](https://coveralls.io/repos/github/hntd187/spotify/badge.svg?branch=master)](https://coveralls.io/github/hntd187/spotify?branch=master)

A Scala library for the Spotify API. The documentation on the Spotify API can be found [here](https://developer.spotify.com/web-api). It currently
supports most features surrounding artists, tracks, and albums. Currently no user support is implemented. 

##### Adding to project
`libraryDependencies += "io.scarman" %% "spotify-api" % "0.2.0"` 

or if you are using scalaJS

`libraryDependencies += "io.scarman" %%% "spotify-api" % "0.2.0"` 


##### Using the Library
There are 2 ways to make use of this library and there are some important things to keep in mind when using this library.
The packages of this library are separated into request and response. Not surprisingly these are for the request portion
and response portion of the API. It's important to understand this so you use the right classes. 

##### First use
The first way uses the `Spotify` object directly to create requests.
No matter which way you use you must have an [sttp](https://github.com/softwaremill/sttp) implicit backend in scope. Currently this API supports any
backend implementing `Backend[Future[R], _]`, which is `AsyncHttpClientFutureBackend` for JVM usage or `FetchBackend` if you are using JS.
```scala
import scala.concurrent.Future
import io.scarman.spotify._
import io.scarman.spotify.{response => resp}
import com.softwaremill.sttp.asynchttpclient.future.AsyncHttpClientFutureBackend

val appId = ""
val appSecret = ""
implicit val backend = AsyncHttpClientFutureBackend()

val spotify = Spotify(appId, appSecret)

val artistId = ""
val artist: Artist = spotify.getArtist(artistId)
val response: Future[resp.Artist] = artist()
```

##### Second Way
The second way uses the Spotify object implicitly and creates the case classes directly.

```scala
import scala.concurrent.Future
import io.scarman.spotify._
import io.scarman.spotify.{response => resp}
import com.softwaremill.sttp.asynchttpclient.future.AsyncHttpClientFutureBackend

val appId = ""
val appSecret = ""
implicit val backend = AsyncHttpClientFutureBackend()

implicit val spotify = Spotify(appId, appSecret)

val artistId = ""
val artist: Artist = Artist(artistId)
val response: Future[resp.Artist] = artist()
```

In these examples, we create the initial entrance point via `Spotify` and then create a request for an Artist. The
response from this request would be the serialized result of the Future. If you've used dispatch before this should look
familiar to you as the request isn't actually made until the `.apply()`  method is called on the request object. It's the
users responsibility to deal with the future at this point, but some of the response objects have methods on them which
make subsequent requests using information you've already provided much easier.

```scala
import io.scarman.spotify._

val artist = Artist("id")
val albums = artist.albums()

val track = Track("id")
val features = track.getAudioFeatures()
```

Currently most of the non-user related API requests are implemented. This includes artists, albums, tracks. Down the road I plan to finish off the rest of the API, but I'm not sure how the user calls will fit in down the road. I'm open to suggestions on them.
Now that I have a working JS implementation it will probably make sense implement the rest of the api for more user driven JS applications to make use of.

#### Contact
Stephen Carman <shcarman@gmail.com>
