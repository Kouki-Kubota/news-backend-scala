package generator

import java.net.URI

import slick.codegen.SourceCodeGenerator
import slick.model.Model

import slick.jdbc.meta.MTable
import slick.jdbc.MySQLProfile
import slick.jdbc.JdbcProfile
import java.net.URI
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration
import slick.model.Model
import javax.swing.table.TableModel
import slick.model.Table

object SlickModelGenerator {
  //tableNamesをNoneにすると全テーブル出力
  def run(slickDriver: String, jdbcDriver: String, url: String, user: String, password: String,
          tableNames: Option[Seq[String]], outputDir: String = "app", pkg: String = "models", topTraitName: String = "Tables", scalaFileName: String = "Tables.scala") = {
    val driver: JdbcProfile = slick.jdbc.MySQLProfile
    val db = slick.jdbc.MySQLProfile.api.Database.forURL(url, driver = jdbcDriver, user = user, password = password)
    try {
      import scala.concurrent.ExecutionContext.Implicits.global
//      val mTablesAction = MTable.getTables.map{_.map{mTable => mTable.copy(name = mTable.name.copy(catalog=None))}}

      val modelAction = driver.createModel(Some(driver.defaultTables), ignoreInvalidDefaults = false)(ExecutionContext.global).withPinnedSession
      val allModel = Await.result(db.run(modelAction),Duration.Inf)
      val modelFiltered=tableNames.fold (allModel){ tableNames =>
        Model(tables = allModel.tables.filter { aTable =>
          tableNames.contains(aTable.name.table)
        })
      }

      new SourceCodeGeneratorEx(modelFiltered).writeToFile(slickDriver, outputDir, pkg, topTraitName, scalaFileName)
    } finally db.close
  }

  def main(args: Array[String]): Unit = {
    //各スキーマの設定でfunction作ってここにいれる
//    exportCommonSchema
    exportNewsAppSchema
  }

  //news_appスキーマの出力
  def exportNewsAppSchema = {
    val slickDriver = "slick.jdbc.MySQLProfile"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/news_app?useSSL=false"
    val user = "news_user"
    val password = "Koukih11!"
    val outputDir = "app"
    val pkg = "models"
    val topTraitName = "tables"
    val scalaFileName = "Tables.scala"

    //  対象テーブル
    val tableNames: Option[Seq[String]] = Some(
      //大文字小文字の判定あるため注意
    Seq("BOOKMARK")
    )

    run(slickDriver, jdbcDriver, url, user, password, tableNames, outputDir, pkg, topTraitName, scalaFileName)
  }
}
