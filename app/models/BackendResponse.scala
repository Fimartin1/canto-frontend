package models

import play.api.libs.json.Json

case class BackendResponse(sentimentData: Seq[SentimentData],
                           confidenceLevel: ConfidenceLevel)

object BackendResponse {
  implicit val formats = Json.format[BackendResponse]
}
