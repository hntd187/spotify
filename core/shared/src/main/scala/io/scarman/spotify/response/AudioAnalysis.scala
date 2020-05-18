package io.scarman.spotify.response

import io.scarman.spotify.util.TrackDuration

case class Bar(start: Double, duration: Double, confidence: Double) extends TrackDuration

case class Beat(start: Double, duration: Double, confidence: Double) extends TrackDuration

case class Meta(analyzer_version: String,
                platform: String,
                detailed_status: String,
                status_code: Option[Int],
                timestamp: Long,
                analysis_time: Double,
                input_process: String)

case class Section(start: Option[Double],
                   duration: Double,
                   confidence: Double,
                   loudness: Double,
                   tempo: Double,
                   tempo_confidence: Double,
                   key: Int,
                   key_confidence: Double,
                   mode: Option[Int],
                   mode_confidence: Double,
                   time_signature: Int,
                   time_signature_confidence: Double)

case class Segment(start: Option[Double],
                   duration: Double,
                   confidence: Option[Double],
                   loudness_start: Double,
                   loudness_max_time: Option[Double],
                   loudness_max: Double,
                   loudness_end: Option[Int],
                   pitches: List[Double],
                   timbre: List[Double])

case class Tatum(start: Double, duration: Double, confidence: Double) extends TrackDuration

case class AnalysisTrack(num_samples: Option[Int],
                         duration: Double,
                         sample_md5: Option[String],
                         offset_seconds: Option[Int],
                         window_seconds: Option[Int],
                         analysis_sample_rate: Int,
                         analysis_channels: Int,
                         end_of_fade_in: Double,
                         start_of_fade_out: Double,
                         loudness: Double,
                         tempo: Double,
                         tempo_confidence: Double,
                         time_signature: Int,
                         time_signature_confidence: Double,
                         key: Int,
                         key_confidence: Double,
                         mode: Int,
                         mode_confidence: Double,
                         codestring: String,
                         code_version: Double,
                         echoprintstring: String,
                         echoprint_version: Double,
                         synchstring: String,
                         synch_version: Double,
                         rhythmstring: String,
                         rhythm_version: Double)
    extends TrackDuration

case class AudioAnalysis(bars: List[Bar],
                         beats: List[Beat],
                         sections: List[Section],
                         segments: List[Segment],
                         tatums: List[Tatum],
                         track: AnalysisTrack) {

  def numBars: Int     = bars.length
  def numBeats: Int    = beats.length
  def numSections: Int = sections.length
  def numSegments: Int = segments.length
  def numTatums: Int   = tatums.length

  def averageBarLength: Double     = bars.map(_.duration).sum / numBars
  def averageBarConfidence: Double = bars.map(_.confidence).sum / numBars

}
