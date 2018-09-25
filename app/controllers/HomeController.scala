package controllers

import connectors.SomeServiceConnector
import forms.MessageInputForm.messageInputForm
import javax.inject._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import views.html._


@Singleton
class HomeController @Inject()(override val messagesApi: MessagesApi,
                               cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  def showHomepage: Action[AnyContent] = Action { implicit request =>
    Ok(canto_homepage())
  }


}