package controllers.endpoints

import dao.BookmarkDao
import dto.BookmarkDto
import play.api.i18n.I18nSupport
import play.api.libs.ws._
import play.api.libs.circe.Circe
import io.circe.syntax._
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

class BookmarkController @Inject()(dao: BookmarkDao, ws: WSClient, val cc: ControllerComponents, ec: ExecutionContext) extends AbstractController(cc) with Circe {
  def index() = Action { implicit request =>
    val bookmarks = dao.fetchAll().map(bookmark => BookmarkDto(bookmark.bookmark_id, bookmark.user_id, bookmark.image_url, bookmark.article_title, bookmark.article_url))
    Ok(bookmarks.asJson)
  }
}
