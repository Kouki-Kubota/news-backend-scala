package dto

import io.circe.{Encoder}
import io.circe.generic.semiauto._

final case class BookmarkDto(id: Option[Int], user_id: Long, urlToImage: String, title: String, url: String)

object BookmarkDto {
  implicit val encoder: Encoder[BookmarkDto] = deriveEncoder
}