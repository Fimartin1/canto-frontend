package controllers

import connectors.CantoConnector
import models.{BackendResponse, ConfidenceLevel, SentimentData}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

class HomeControllerSpec extends ControllerTestResources {

  private val mockCantoConnector = mock[CantoConnector]
  private val templateController = new HomeController(mockCantoConnector, messagesApi, mockCC)

  private val sentimentData: Seq[SentimentData] = Seq(
    SentimentData("", 2.2, "", ""),
    SentimentData("", 2.2, "", ""),
    SentimentData("", 2.2, "", ""))
  private val confidenceLevel = ConfidenceLevel(2.2, 2.2, 2.2, None)
  private val cantoResponse = BackendResponse(sentimentData, confidenceLevel)

  "TemplateController GET /" should {

    "return OK and show the homepage" in {
      (mockCantoConnector.getData()(_: ExecutionContext)).expects(*).returns(Future successful cantoResponse)

      val request = templateController.getHomepage.apply(FakeRequest())

      status(request) shouldBe OK
      contentType(request) shouldBe Some("text/html")
      contentAsString(request) should include("Home")
      contentAsString(request) should not include "Response:"
    }
  }
}