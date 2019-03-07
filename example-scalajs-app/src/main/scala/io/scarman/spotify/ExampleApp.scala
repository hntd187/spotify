package io.scarman.spotify

import com.softwaremill.sttp._
import io.scarman.spotify.auth.ClientCredentials
import org.scalajs.dom._
import scalatags.Text.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import Elements._

object ExampleApp {

  val appId                   = "2a40953e7c854dd48d257a10ad6de863"
  val appSecret               = "00115daaef1a4e209c87c8805643c7d2"
  val sweet_pitbull_album     = "4aawyAB9vmqN3uQ7FjRGTy"
  val schpoopy_mastodon_album = "1n8QZFcwx5aQ2LIIlj0iYe"

  implicit val backend = FetchBackend()
  implicit val auth    = ClientCredentials(appId, appSecret)
  implicit val spotify = Spotify(appId, appSecret)

  def reqAlbum(id: String) = {
    val album_fut = Album(id)
    album_fut().andThen {
      case Success(album) =>
        document.body.innerHTML = br.render + div(searchBar(schpoopy_mastodon_album)) + div(
          `class` := "card text-white bg-dark mb-3 w-25 mx-auto",
          img(src := album.images.head.asInstanceOf[response.Image].url, `class` := "card-img-top"),
          div(`class` := "card-body", h5(`class` := "card-title", album.name), "By: ", album.artists.map(_.name).mkString(", ")),
          ul(`class` := "list-group list-group-flush", for (track <- album.tracks.items) yield createItem(track))
        ).render + br
      case Failure(exception) => throw exception
    }
  }

  def main(args: Array[String]): Unit = {
    reqAlbum(schpoopy_mastodon_album)
  }
}
