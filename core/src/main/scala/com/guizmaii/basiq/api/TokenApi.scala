package com.guizmaii.basiq.api

import enumeratum._
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.Codec

sealed abstract class TokenApiError

sealed trait TokenType extends EnumEntry
object TokenType extends Enum[TokenType] with CirceEnum[TokenType] {
  override final val values = findValues

  final case object Bearer extends TokenType
}

final case class Token(value: NonEmptyString)
final case class TokenResponse(access_token: Token, token_type: TokenType, expires_in: PosLong) // TODO Jules: Should be a FiniteDuration.

object Token {
  import io.circe.generic.extras.semiauto._
  import io.circe.refined._

  implicit final val codec: Codec[Token] = deriveUnwrappedCodec
}
object TokenResponse {
  import io.circe.generic.semiauto._
  import io.circe.refined._

  implicit final val codec: Codec[TokenResponse] = deriveCodec
}

trait TokenApi[F[_]] {

  def newToken: F[Either[TokenApiError, TokenResponse]]

}
