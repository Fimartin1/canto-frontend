package connectors

import javax.inject.{Inject, Singleton}
import models.BackendResponse
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CantoConnector @Inject()(ws: WSClient,
                               config: Configuration) {

  private val serviceName = "canto"
  private lazy val env = config.get[String]("run.mode")
  private lazy val host = config.get[String](s"$env.services.$serviceName.host")

  private lazy val baseUrl = env match {
    case "Prod" => s"http://$host/$serviceName"
    case _ =>
      val port = config.get[Int](s"$env.services.$serviceName.port")
      s"http://$host:$port"
  }

  private def buildUrl(endpoint: String) = s"$baseUrl$endpoint"

  def getData()(implicit ec: ExecutionContext): Future[BackendResponse] = {
    ws.url(buildUrl("/latest")).get() map {
      response =>
        response.status match {
          case 200 => Json.parse(response.body).as[BackendResponse]
          case _ => throw new Exception
        }
    }
  }
}