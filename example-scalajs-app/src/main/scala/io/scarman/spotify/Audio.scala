package io.scarman.spotify

import org.scalajs.dom.*
import scalatags.Text.all.*

import scala.scalajs.js.annotation.JSExportTopLevel

object Audio {

  private var currentlyPlaying: Option[String] = None

  @JSExportTopLevel("pauseSound")
  def pauseSound(ele: String): Unit = {
    val element = document.getElementById(ele)
    if (element != null) {
      val audioTag = document.getElementById(s"$ele-track").asInstanceOf[HTMLAudioElement]
      audioTag.pause()
      audioTag.currentTime = 0
      element.innerHTML = i(`class` := "material-icons md-24", onclick := s"playExistingSound('$ele')", "play_circle_filled").render
    }
  }

  @JSExportTopLevel("playExistingSound")
  def playExistingSound(ele: String): Unit = {
    val element  = document.getElementById(ele)
    val audioTag = document.getElementById(s"$ele-track").asInstanceOf[HTMLAudioElement]
    audioTag.onended = { (_: Event) => { pauseSound(ele) } }
    if (audioTag.ended) {
      audioTag.currentTime = 0
    }
    audioTag.volume = 0.25
    audioTag.play()
    if (currentlyPlaying.getOrElse("") != ele) {
      currentlyPlaying.foreach(pauseSound)
      currentlyPlaying = Some(ele)
    }
    element.innerHTML = i(`class` := "material-icons md-24", onclick := s"pauseSound('$ele')", "pause").render
  }

  @JSExportTopLevel("playSound")
  def playSound(ele: String, url: String): Unit = {
    val a       = audio(src := url, id := s"$ele-track").render
    val element = document.getElementById(ele)
    element.insertAdjacentHTML("afterend", a)
    playExistingSound(ele)
  }

}
