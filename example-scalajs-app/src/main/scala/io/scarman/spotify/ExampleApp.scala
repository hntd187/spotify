package io.scarman.spotify

import io.scarman.spotify.auth.{AuthorizationCode, ImplicitCredentials, Scopes}
import io.scarman.spotify.http.Authorization
import io.scarman.spotify.request.{Me, Search}
import org.scalajs.dom.html.*
import org.scalajs.dom.{document, window}
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.*
import scalatags.Text.TypedTag
import scalatags.Text.all.*
import sttp.capabilities
import sttp.client3.{FetchBackend, SttpBackend}

import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.util.{Failure, Success, Try}

object ExampleApp {

  val appId                   = "2a40953e7c854dd48d257a10ad6de863"
  val sweet_pitbull_album     = "4aawyAB9vmqN3uQ7FjRGTy"
  val schpoopy_mastodon_album = "1n8QZFcwx5aQ2LIIlj0iYe"

  val buttonClass             = "text-center list-group-item list-group-item-action list-group-item-dark"
  val auth: AuthorizationCode = AuthorizationCode(appId, Scopes.All, "http://localhost:63342/spotify/example/index-dev.html")

  implicit val creds: ImplicitCredentials                            = ImplicitCredentials()
  implicit val backend: SttpBackend[Future, capabilities.WebSockets] = FetchBackend()

  @JSExportTopLevel("main")
  def main(args: Array[String]): Unit = {
    if (!AuthorizationCode.parseUrl(window.location.href).contains("access_token")) {
      window.location.replace(auth.authUrl())
    }

    me().flatMap(id => reqAlbum("body", id))
  }

  @JSExportTopLevel("getSearchAlbum")
  def getSearchAlbum(): Unit = {
    val inp = document.getElementById("idButton").asInstanceOf[Input]
    reqAlbum("body", inp.value)
  }

  @JSExportTopLevel("getAlbum")
  def getAlbum(elem: String, id: String): Unit = reqAlbum(elem, id)

  def createAlbumCard(album: response.Album): TypedTag[String] = {
    val i       = document.getElementById("idButton")
    if (i != null) {
      i.asInstanceOf[Input].value = album.name
    }
    val artwork = div(
      `class`    := "card mx-auto w-25 p-0",
      attr("id") := "results-container",
      style      := "margin-top: -15px",
      ul(`class` := "list-group list-group-flush", attr("id") := "results")
    )
    val tracks  = div(
      `class` := "card text-white bg-dark mb-3 w-25 mx-auto",
      img(src     := album.images.head.asInstanceOf[response.Image].url, `class` := "card-img-top"),
      div(`class` := "card-body", h5(`class` := "card-title", album.name), "By: ", album.artists.map(_.name).mkString(", ")),
      ul(`class`  := "list-group list-group-flush", for (track <- album.tracks.items) yield createItem(track))
    )
    div(artwork, tracks, div(`class` := "mb-3 w-25 mx-auto", id := "foot"))
  }

  def me(): Future[String] = {
    val m = Me().currentlyPlaying()
    m()
      .map(m => m.item.album.get.id)
      .recover { case e =>
        println(e.getMessage)
        println(e.getCause)
        schpoopy_mastodon_album
      }
  }

  @JSExportTopLevel("reqAlbum")
  def reqAlbum(elem: String, id: String): Future[response.Album] = {
    val album_fut = Album(id)

    album_fut().andThen {
      case Success(album)     =>
        document.getElementById(elem).innerHTML = div(br, searchBar(album.name), createAlbumCard(album)).toString()
        focusChange("results-container", "none")
      case Failure(exception) => exception
    }
  }

  def searchBar(v: String): TypedTag[String] = {
    val buttonDiv =
      div(`class` := "input-group-prepend", button(`class` := "btn btn-outline-secondary", onclick := "getSearchAlbum()", "Find"))
    val inputBox  = input(value := v, `type` := "text", `class` := "form-control", id := "idButton", onkeyup := "taEvent(false)")
    div(`class` := "list-group list-group-horizontal mb-3 mx-auto w-25", inputBox, buttonDiv)
  }

  def cleanDuration(i: Long): String = {
    val base    = Duration(i, TimeUnit.MILLISECONDS)
    val mins    = base.toSeconds / 60
    val seconds = (base - Duration(mins, TimeUnit.MINUTES)).toSeconds
    "%d:%02d".format(mins, seconds)
  }

  def createItem(track: response.Track): TypedTag[String] = {
    val eleId = s"item-${track.track_number}"
    li(
      `class` := "d-flex list-group-item list-group-item-dark",
      span(`class` := "flex-grow-1", track.track_number, ". ", b(track.name)),
      span(cleanDuration(track.duration_ms), " "),
      for (preview <- track.preview_url)
        yield span(id := eleId, i(`class` := "material-icons md-24", onclick := s"playSound('$eleId', '$preview')", "play_circle_filled"))
    )
  }

  @JSExportTopLevel("focusChange")
  def focusChange(e: String, f: String): Unit = {
    val ele = document.getElementById(e)
    if (ele != null) {
      ele.asInstanceOf[Div].style.display = f
    }
  }

  @JSExportTopLevel("taEvent")
  def taEvent(clear: Boolean = false): Unit = {
    if (clear) return

    val t = document.getElementById("idButton")
    if (t != null) {
      val s = Search(s"album:${t.asInstanceOf[Input].value}", "album", limit = 5)
      s().onComplete {
        case Success(v) =>
          val list  = document.getElementById("results")
          val items = v.albums.map(_.items.map(a => (a.id, a.name))).getOrElse(List.empty[(String, String)])
          list.innerHTML = items
            .map(i => ul(`class` := buttonClass, onclick := s"reqAlbum('body','${i._1}')", i._2))
            .mkString("")
          focusChange("results-container", "block")
        case Failure(_) =>
      }
    }
  }
}
