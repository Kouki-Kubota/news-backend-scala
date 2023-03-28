 package controllers.endpoints

import dao.{BookmarkDao, BookmarkSlickDao}
import dto.BookmarkDto
import play.api.i18n.I18nSupport
import play.api.libs.ws._
import play.api.libs.circe.Circe
import io.circe.syntax._
import models.tables.Bookmark
import play.api.Logger
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

//class BookmarkController @Inject()(dao: BookmarkDao, ws: WSClient, val cc: ControllerComponents, ec: ExecutionContext) extends AbstractController(cc) with Circe {
//  def index() = Action { implicit request =>
//    val bookmarks = dao.fetchAll().map(bookmark => BookmarkDto(bookmark.id, bookmark.user_id, bookmark.urlToImage, bookmark.title, bookmark.url))
//    Ok(bookmarks.asJson)
//  }
//
//  def insert(user_id: Long, image_url: String, article_title: String, article_url: String) = Action { implicit request =>
//    dao.insert(user_id, image_url, article_title, article_url)
//    Ok("挿入完了")
//  }
//}

class BookmarkController @Inject()(slickDao: BookmarkSlickDao, ws: WSClient, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {
//  private val logger = Logger(this.getClass)

  //挿入処理
  def index() = Action.async { implicit request: Request[AnyContent] =>
    val bookmarkFetch = slickDao.fetch().map(bookmarkRows => bookmarkRows.map({ bookmarkRow =>  BookmarkDto(bookmarkRow.id, bookmarkRow.user_id, bookmarkRow.urlToImage, bookmarkRow.title, bookmarkRow.url)}))
    logger.debug("bookmarkFetch" + bookmarkFetch)
    bookmarkFetch.map(bookmarks => Ok(bookmarks.asJson))
  }

    def insert(image_url: String, article_title: String, article_url: String) = Action { implicit request =>
      slickDao.create(image_url, article_title, article_url)
      Ok("挿入完了")
    }

  def delete(url: String) = Action { implicit request =>
    logger.debug(url + "確認して")
    slickDao.delete(url)
    Ok("削除完了")
  }
}