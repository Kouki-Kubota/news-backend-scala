package dao

import javax.inject.{Inject, Singleton}
import play.api.db.Database
import dao.BookmarkDao.BookmarkRow

object BookmarkDao {
  case class BookmarkRow(bookmark_id: Long, user_id: Long, image_url: String, article_title: String, article_url: String)
}

class BookmarkDao @Inject()(db: Database) {
  def fetchAll(): Seq[BookmarkRow] = {
    val conn = db.getConnection()

    try {
      //クエリ実行
      val rs = conn.createStatement.executeQuery("SELECT bookmark_id, user_id, image_url, article_title, article_url FROM bookmark")

      //クエリ結果をcase classに設定
      new Iterator[BookmarkRow] {
        def hasNext: Boolean = rs.next()
        def next(): BookmarkRow = {
          BookmarkRow(rs.getLong("bookmark_id"), rs.getLong("user_id"), rs.getString("image_url"), rs.getString("article_title"), rs.getString("article_url"))
        }
      }.toList
    } finally {
      conn.close()
    }
  }
}
