package models

import play.api.libs.json.Json

case class SentimentData(created_at: String,
                         confidence: Double,
                         tag_name: String,
                         text: String)

object SentimentData {
  implicit val formats = Json.format[SentimentData]
}
