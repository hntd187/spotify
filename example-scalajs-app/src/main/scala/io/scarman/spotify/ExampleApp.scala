package io.scarman.spotify

import java.time.Duration
import com.softwaremill.sttp._
import org.scalajs.dom._
import scalacss.DevDefaults._
import scalacss.ScalatagsCss._
import scalatags.Text

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object ExampleCss extends StyleSheet.Inline {
  import dsl._

  val container = style(
    addClassNames("card text-white bg-dark mb-3"),
    margin.auto,
    width(500 px),
    borderRadius(5 px),
  )
}

object ExampleApp {
  import scalatags.Text.all._

  val appId               = "2a40953e7c854dd48d257a10ad6de863"
  val appSecret           = "00115daaef1a4e209c87c8805643c7d2"
  val sweet_pitbull_album = "4aawyAB9vmqN3uQ7FjRGTy"
  implicit val backend    = FetchBackend()
  implicit val spotify    = Spotify(appId, appSecret)

  def spinner: Text.TypedTag[String] = div(`class` := "spinner-border", role := "status", span(`class` := "sr-only", "Loading"))
  def cleanDuration(d: Long)         = Duration.ofMillis(d).toString.replace("PT", "").replace("M", ":").replace("S", "")
  def createItem(track: response.Track) =
    li(`class` := "list-group-item list-group-item-dark", track.track_number, ". ", b(track.name), " - ", cleanDuration(track.duration_ms))

  window.onload = (e: Event) => {
    document.body.innerHTML = spinner.render
  }
  document.head.appendChild(ExampleCss.render)

  def reqAlbum(id: String) = {
    val album_fut = Album(id)
    album_fut().andThen {
      case Success(album) =>
        document.body.innerHTML = br + div(
          ExampleCss.container,
          img(src := album.images.head.asInstanceOf[response.Image].url, `class` := "card-img-top"),
          div(`class` := "card-body", h5(`class` := "card-title", album.name), "By: ", album.artists.map(_.name).mkString(", ")),
          ul(`class` := "list-group list-group-flush", for (track <- album.tracks.items) yield createItem(track))
        ).render + br
      case Failure(exception) => throw exception
    }
  }

  def main(args: Array[String]): Unit = {
    reqAlbum(sweet_pitbull_album)
  }

}
