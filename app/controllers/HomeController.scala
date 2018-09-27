package controllers

import connectors.CantoConnector
import javax.inject._
import models.SentimentData
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(cantoConnector: CantoConnector,
                               override val messagesApi: MessagesApi,
                               cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  val sentimentStates = Seq(
    "Positive",
    "Neutral",
    "Negative"
  )

  def getHomepage: Action[AnyContent] = Action.async { implicit request =>
    cantoConnector.getData() map { response =>
      val (positivePct, neutralPct, negativePct) = sentimentStates.map { sentiment =>
        (response.sentimentData.count(_.tag_name == sentiment).asInstanceOf[Double]
          / response.sentimentData.size.asInstanceOf[Double])* 100.0
      } match { case Seq(pos, neut, neg) => (pos, neut, neg) }

      Ok(homepage(response, positivePct, neutralPct, negativePct))
    }
  }

}