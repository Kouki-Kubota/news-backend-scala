package dto

import io.circe.{Encoder}
import io.circe.generic.semiauto._

final case class BookmarkDto(bookmark_id: Long, user_id: Long, image_url: String, article_title: String, article_url: String)

object BookmarkDto {
  implicit val encoder: Encoder[BookmarkDto] = deriveEncoder
}