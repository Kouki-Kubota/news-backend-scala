package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object tables extends {
  val profile = slick.jdbc.MySQLProfile
} with tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Bookmark.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Bookmark
   *  @param userId Database column USER_ID SqlType(INT)
   *  @param imageUrl Database column IMAGE_URL SqlType(VARCHAR), Length(400,true), Default(None)
   *  @param articleTitle Database column ARTICLE_TITLE SqlType(VARCHAR), Length(400,true)
   *  @param articleUrl Database column ARTICLE_URL SqlType(VARCHAR), Length(400,true)
   *  @param bookmarkId Database column BOOKMARK_ID SqlType(INT), AutoInc, PrimaryKey */
  case class BookmarkRow(userId: Int, imageUrl: Option[String] = None, articleTitle: String, articleUrl: String, bookmarkId: Option[Int] = None)
  /** GetResult implicit for fetching BookmarkRow objects using plain SQL queries */
  implicit def GetResultBookmarkRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[String], e3: GR[Option[Int]]): GR[BookmarkRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[Int], <<?[String], <<[String], <<[String])
    import r._
    BookmarkRow.tupled((_2, _3, _4, _5, _1)) // putting AutoInc last
  }
  /** Table description of table BOOKMARK. Objects of this class serve as prototypes for rows in queries. */
  class Bookmark(_tableTag: Tag) extends profile.api.Table[BookmarkRow](_tableTag, Some("news_app"), "BOOKMARK") {
    def * = (userId, imageUrl, articleTitle, articleUrl, Rep.Some(bookmarkId)) <> (BookmarkRow.tupled, BookmarkRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userId), imageUrl, Rep.Some(articleTitle), Rep.Some(articleUrl), Rep.Some(bookmarkId))).shaped.<>({r=>import r._; _1.map(_=> BookmarkRow.tupled((_1.get, _2, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column USER_ID SqlType(INT) */
    val userId: Rep[Int] = column[Int]("USER_ID")
    /** Database column IMAGE_URL SqlType(VARCHAR), Length(400,true), Default(None) */
    val imageUrl: Rep[Option[String]] = column[Option[String]]("IMAGE_URL", O.Length(400,varying=true), O.Default(None))
    /** Database column ARTICLE_TITLE SqlType(VARCHAR), Length(400,true) */
    val articleTitle: Rep[String] = column[String]("ARTICLE_TITLE", O.Length(400,varying=true))
    /** Database column ARTICLE_URL SqlType(VARCHAR), Length(400,true) */
    val articleUrl: Rep[String] = column[String]("ARTICLE_URL", O.Length(400,varying=true))
    /** Database column BOOKMARK_ID SqlType(INT), AutoInc, PrimaryKey */
    val bookmarkId: Rep[Int] = column[Int]("BOOKMARK_ID", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Bookmark */
  lazy val Bookmark = new TableQuery(tag => new Bookmark(tag))
}
