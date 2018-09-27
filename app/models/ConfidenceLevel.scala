package models

import play.api.libs.json.{Json, OFormat}

case class ConfidenceLevel(averagePositiveConfidence: Option[Double],
                           averageNeutralConfidence: Option[Double],
                           averageNegativeConfidence: Option[Double],
                           time_zone: Option[String])

object ConfidenceLevel {
  implicit val formats: OFormat[ConfidenceLevel] = Json.format[ConfidenceLevel]
}
