package models

import java.time.LocalDate

import play.api.libs.json.Json

case class ConfidenceLevel(averagePositiveConfidence: Double,
                           averageNeutralConfidence: Double,
                           averageNegativeConfidence: Double,
                           time_zone: Option[String])

object ConfidenceLevel {
  implicit val formats = Json.format[ConfidenceLevel]
}
