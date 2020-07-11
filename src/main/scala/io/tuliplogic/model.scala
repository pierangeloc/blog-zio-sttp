package io.tuliplogic

import io.circe.{Codec, Decoder, Encoder}
import upickle.default._
import upickle.default

case class Config(baseUrl: String, port: Int)

case class CustomerId(value: String)
object CustomerId {
  implicit def customerIdRW: default.ReadWriter[CustomerId] = readwriter[String].bimap[CustomerId](_.value, CustomerId(_))
  implicit val emailCodec: Codec[CustomerId] = Codec.from(Decoder.decodeString.map(CustomerId(_)), Encoder.encodeString.contramap(_.value))

}
case class Email(value: String)
object Email {
  implicit def emailRW: default.ReadWriter[Email] = readwriter[String].bimap[Email](_.value, Email(_))
  implicit val emailCodec: Codec[Email] = Codec.from(Decoder.decodeString.map(Email(_)), Encoder.encodeString.contramap(_.value))
}
case class Customer(customerId: CustomerId, email: Email, promotionOptIn: Boolean)
object  Customer {
  implicit def userRW: ReadWriter[Customer] = macroRW[Customer]
}