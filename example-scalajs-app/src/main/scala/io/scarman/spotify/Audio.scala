package io.scarman.spotify

import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLAudioElement
import scalatags.Text.all._

import scala.scalajs.js.annotation.JSExportTopLevel

object Audio {

  @JSExportTopLevel("pauseSound")
  def pauseSound(ele: String) = {
    val element  = document.getElementById(ele)
    val audioTag = document.getElementById(s"$ele-track").asInstanceOf[HTMLAudioElement]
    audioTag.pause()
    element.innerHTML = i(`class` := "material-icons md-24", onclick := s"playExistingSound('$ele')", "play_circle_filled").render
  }

  @JSExportTopLevel("playExistingSound")
  def playExistingSound(ele: String) = {
    val element  = document.getElementById(ele)
    val audioTag = document.getElementById(s"$ele-track").asInstanceOf[HTMLAudioElement]
    audioTag.onended = (_: Event) => { pauseSound(ele) }
    if (audioTag.ended) {
      audioTag.currentTime = 0
    }
    audioTag.volume = 0.25
    audioTag.play()
    element.innerHTML = i(`class` := "material-icons md-24", onclick := s"pauseSound('$ele')", "pause").render
  }

  @JSExportTopLevel("playSound")
  def playSound(ele: String, url: String) = {
    val a       = audio(src := url, id := s"$ele-track").render
    val element = document.getElementById(ele)
    element.insertAdjacentHTML("afterend", a)
    playExistingSound(ele)
  }

}
