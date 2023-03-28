package dao

import javax.inject.{Inject, Singleton}
import java.sql.Connection
import play.api.db.Database
import play.api.Logger
import scala.util.control.NonFatal
import dao.BookmarkDao.BookmarkRow

object BookmarkDao {
  case class BookmarkRow(id: Option[Int], user_id: Long, urlToImage: String, title: String, url: String)
}

class BookmarkDao @Inject()(db: Database) {
  //bookmark取得処理
//  def fetchAll(): Seq[BookmarkRow] = {
//    val conn = db.getConnection()
//
//    try {
//      //クエリ実行
//      val rs = conn.createStatement.executeQuery("SELECT bookmark_id, user_id, image_url, article_title, article_url FROM bookmark")
//
//      //クエリ結果をcase classに設定
//      new Iterator[BookmarkRow] {
//        def hasNext: Boolean = rs.next()
//        def next(): BookmarkRow = {
//          BookmarkRow(rs.getInt("bookmark_id"), rs.getLong("user_id"), rs.getString("image_url"), rs.getString("article_title"), rs.getString("article_url"))
//        }
//      }.toList
//    } finally {
//      conn.close()
//    }
//  }

  //bookmark登録処理
  def insert(user_id: Long, image_url: String, article_title: String, article_url: String): Int = {
    operateConnection { conn =>
      val stmt = conn.prepareStatement("INSERT INTO bookmark (user_id, image_url, article_title, article_url) VALUES (?,?,?,?)")
      stmt.setLong(1, user_id)
      stmt.setString(2, image_url)
      stmt.setString(3, article_title)
      stmt.setString(4, article_url)
      stmt.executeUpdate()
    }
  }

  private def operateConnection[A](op: Connection => A): A = {
    val conn = db.getConnection()
    try {
      op(conn)
    } catch {
      case NonFatal(e) =>
        throw e
    }
    finally {
      conn.close()
    }
  }
}
