package io.scarman.spotify.request

import com.softwaremill.sttp._
import io.scarman.spotify._
import io.scarman.spotify.http.{Authorization, HttpRequest}

import scala.concurrent.Future

case class TopTracks(id: String, country: String = "ES")(implicit auth: Authorization, backend: SttpBackend[Future, Nothing])
    extends HttpRequest[response.Tracks] {
  lazy protected val reqUri = uri"$base$AR/$id/top-tracks".param("country", country)
}
