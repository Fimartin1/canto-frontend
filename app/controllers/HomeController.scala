package controllers

import connectors.SomeServiceConnector
import javax.inject._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import views.html._

@Singleton
class HomeController @Inject()(someServiceConnector: SomeServiceConnector,
                               override val messagesApi: MessagesApi,
                               cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  def getHomepage: Action[AnyContent] = Action { implicit request =>
    Ok(homepage())
  }

}