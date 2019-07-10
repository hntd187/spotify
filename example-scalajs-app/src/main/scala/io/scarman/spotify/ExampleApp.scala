package io.scarman.spotify

import java.util.concurrent.TimeUnit

import com.softwaremill.sttp._
import io.scarman.spotify.Elements._
import io.scarman.spotify.auth._
import io.scarman.spotify.http.Authorization
import org.scalajs.dom._
import org.scalajs.dom.html.Div
import scalatags.JsDom
import scalatags.JsDom.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.scalajs.js.annotation.{JSExportStatic, ScalaJSDefined}
import scala.util.{Failure, Success}

@ScalaJSDefined
class ExampleApp(implicit authorization: Authorization) extends scalajs.js.Object {

  def searchDropdown(app: ExampleApp, default: String)(implicit auth: Authorization): JsDom.TypedTag[Div] = {
    val mast = a(
      href := "#",
      `class` := "text-center list-group-item list-group-item-action list-group-item-dark",
      "Mastodon",
      onclick := s"reqAlbum('body', '${ExampleApp.schpoopy_mastodon_album}')"
    )
    val pit = a(
      href := "#",
      `class` := "text-center list-group-item list-group-item-action list-group-item-dark",
      "Pitbull",
      onclick := s"reqAlbum('body', '${ExampleApp.sweet_pitbull_album}')"
    )

    div(`class` := "list-group list-group-horizontal mb-3 mx-auto w-25", mast, pit)
  }

  def cleanDuration(i: Long) = {
    val base    = Duration(i, TimeUnit.MILLISECONDS)
    val mins    = base.toSeconds / 60
    val seconds = (base - Duration(mins, TimeUnit.MINUTES)).toSeconds
    "%d:%02d".format(mins, seconds)
  }

  def createItem(track: response.Track) = {
    val eleId = s"item-${track.track_number}"
    li(
      `class` := "d-flex list-group-item list-group-item-dark",
      span(`class` := "flex-grow-1", track.track_number, ". ", b(track.name)),
      span(cleanDuration(track.duration_ms), " "),
      for (preview <- track.preview_url)
        yield span(id := eleId, i(`class` := "material-icons md-24", onclick := s"playSound('$eleId', '$preview')", "play_circle_filled"))
    )
  }
}

object ExampleApp {

  val appId     = "2a40953e7c854dd48d257a10ad6de863"
  val appSecret = "00115daaef1a4e209c87c8805643c7d2"

  val sweet_pitbull_album     = "4aawyAB9vmqN3uQ7FjRGTy"
  val schpoopy_mastodon_album = "1n8QZFcwx5aQ2LIIlj0iYe"

  @JSExportStatic
  def reqAlbum(elem: String, id: String)(implicit authorization: Authorization, backend: SttpBackend[Future, Nothing]) = {
    val album_fut = Album(id)
    val search    = searchDropdown(id).toString()
    album_fut().andThen {
      case Success(album) =>
        document.getElementById(elem).innerHTML = search.toString + div(
          `class` := "card text-white bg-dark mb-3 w-25 mx-auto",
          img(src := album.images.head.asInstanceOf[response.Image].url, `class` := "card-img-top"),
          div(`class` := "card-body", h5(`class` := "card-title", album.name), "By: ", album.artists.map(_.name).mkString(", ")),
          ul(`class` := "list-group list-group-flush", for (track <- album.tracks.items) yield createItem(track))
        ).toString()
      case Failure(exception) => throw exception
    }
  }

  def main(args: Array[String]): Unit = {
    val auth = AuthorizationCode(appId, Scopes.All, "http://localhost:8080/index-dev.html")

    if (!AuthorizationCode.parseUrl(window.location.href).contains("access_token")) {
      window.location.replace(auth.authUrl().toString())
    } else {
      implicit val creds   = ImplicitCredentials()
      implicit val backend = FetchBackend()

      val app = new ExampleApp()
      reqAlbum("body", sweet_pitbull_album)
    }
  }
}
