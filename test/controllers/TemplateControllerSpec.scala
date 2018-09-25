package controllers

import connectors.SomeServiceConnector
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

class TemplateControllerSpec extends ControllerTestResources {

  private val mockSomeServiceConnector = mock[SomeServiceConnector]
  private val templateController = new TemplateController(mockSomeServiceConnector, messagesApi, mockCC)

  "TemplateController GET /" should {

    "return OK and show the homepage without a MESSAGE session" in {

      val request = templateController.getHomepage.apply(FakeRequest())

      status(request) shouldBe OK
      contentType(request) shouldBe Some("text/html")
      contentAsString(request) should include("Home")
      contentAsString(request) should not include "Response:"
    }

    "return OK and show the homepage with a MESSAGE session" in {

      val request = templateController.getHomepage.apply(FakeRequest().withSession("MESSAGE" -> "test1"))

      status(request) shouldBe OK
      contentType(request) shouldBe Some("text/html")
      contentAsString(request) should include("Home")
      contentAsString(request) should include("Response: test1")
    }
  }

  "TemplateController POST /" should {

    "redirect to getHomepage with successful form data" in {
      (mockSomeServiceConnector.sendMessage(_: String)(_: ExecutionContext)).expects(*, *).returns(Future successful "response")

      val request = templateController.submitMessage.apply(FakeRequest()
        .withFormUrlEncodedBody(validMessageInputForm: _*))

      status(request) shouldBe SEE_OTHER
      redirectLocation(request).get shouldBe routes.TemplateController.getHomepage().url
    }

    "return BAD_REQUEST and show form with errors when form data has errors" in {
      val request = templateController.submitMessage.apply(FakeRequest()
        .withFormUrlEncodedBody(invalidMessageInputForm: _*))

      status(request) shouldBe BAD_REQUEST
      contentAsString(request) should include("You must enter a value")
    }

    "return BAD_REQUEST and show form with errors when missing form data" in {
      val request = templateController.submitMessage.apply(FakeRequest())

      status(request) shouldBe BAD_REQUEST
    }
  }
}
