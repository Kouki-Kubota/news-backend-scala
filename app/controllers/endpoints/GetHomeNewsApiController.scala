package controllers.endpoints

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.i18n.I18nSupport
import play.api.libs.ws._
import play.api.mvc.{AbstractController, ControllerComponents}

class GetHomeNewsApiController @Inject() (ws: WSClient, val cc: ControllerComponents, ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {
  def getHomeNewsApi() = Action.async {
    val url: String = "https://newsapi.org/v2/top-headlines?country=jp&sortBy=publishedAt&apiKey="
    val key: String = "540c0a2d08264331b2101b7c7169ed92"
    ws.url(url + key).get().map { response =>
      Ok(response.body)
    }
  }
}
