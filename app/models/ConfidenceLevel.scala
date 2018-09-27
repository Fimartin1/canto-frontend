package models

import java.time.LocalDate

case class ConfidenceLevel(averagePositiveConfidence: Int,
                         averageNeutralConfidence: Int,
                         averageNegativeConfidence: Int,
                         timeZone:  Option[LocalDate])
