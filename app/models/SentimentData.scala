package models

import java.time.LocalDate

case class SentimentData(createAt: LocalDate,
                         confidenceLevel: Int,
                         tagName: String,
                         text: String)
