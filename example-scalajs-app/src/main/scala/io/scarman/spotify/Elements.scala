package io.scarman.spotify

import java.util.concurrent.TimeUnit

import io.scarman.spotify.http.Authorization
import org.scalajs.dom.html.Div
import scalatags.JsDom
import scalatags.JsDom.all._

import scala.concurrent.duration.Duration

object Elements {

  def searchDropdown(default: String)(implicit auth: Authorization): JsDom.TypedTag[Div] = {
    val mast = a(href := "#", `class` := "text-center list-group-item list-group-item-action list-group-item-dark", "Mastodon").render
    val pit  = a(href := "#", `class` := "text-center list-group-item list-group-item-action list-group-item-dark", "Pitbull").render

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
