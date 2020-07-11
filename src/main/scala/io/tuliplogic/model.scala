package io.tuliplogic

import upickle.default._

case class UserId(value: String)
object UserId {
  implicit def userIdRW = readwriter[String].bimap[UserId](_.value, UserId(_))
}
case class Email(value: String)
object Email {
  implicit def emailRW = readwriter[String].bimap[Email](_.value, Email(_))
}
case class User(userId: UserId, email: Email, promotionOptIn: Boolean)
object  User {
  implicit def userRW: ReadWriter[User] = macroRW[User]
}