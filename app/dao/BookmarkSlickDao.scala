package dao
//import dao.BookmarkDao.BookmarkRow

import models.tables._

import javax.inject.{Inject, Singleton}
//import models.tables.Bookmark
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._


import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

@Singleton
class BookmarkSlickDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
    private val logger = Logger(this.getClass)
  def fetch(): Future[Seq[BookmarkDao.BookmarkRow]] = {
    //SELECTクエリ
    val action = Bookmark.result.map(rows => {
      rows.map(row => {
        BookmarkDao.BookmarkRow(
          row.bookmarkId,
          row.userId,
          row.imageUrl.getOrElse(""),
          row.articleTitle,
          row.articleUrl)
      })
    })

    db.run(action).recover {
      case NonFatal(e) =>
        logger.error("DB操作でエラー発生", e)
        throw e
    }
  }

  def create(imageUrl: String, articleTitle: String, articleUrl: String): Future[Int] = {
    //Insertクエリ
    val action = Bookmark += BookmarkRow(1, Some(imageUrl), articleTitle, articleUrl)

    //クエリ実行
    db.run(action).recover {
      case NonFatal(e) =>
        logger.error("DB操作で例外が発生しました。", e)
        throw e
    }
  }

  def delete(url: String): Future[Int] = {
    //DELETEクエリ
    val action = Bookmark.filter(_.articleUrl === url.bind).delete

    //クエリ実行
    db.run(action).recover {
      case NonFatal(e) =>
        logger.error("DB操作で例外が発生しました", e)
        throw e
    }
  }
}