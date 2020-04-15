package com.guizmaii.basiq.api

import cats.Eq
import eu.timepit.refined.types.numeric.PosLong
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.testing.ArbitraryInstances
import io.circe.testing.golden.GoldenCodecTests
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FlatSpecDiscipline

object TokenApiGoldenTest {
  import enumeratum.scalacheck._

  implicit val arbToken: Arbitrary[Token] = Arbitrary(
    Gen.nonEmptyListOf[Char](Gen.alphaChar).map(_.mkString).map(NonEmptyString.unsafeFrom).map(Token(_))
  )

  implicit final val arbTokenResponse: Arbitrary[TokenResponse] = Arbitrary {
    for {
      accessToken <- arbitrary[Token]
      tokenType   <- arbitrary[TokenType]
      expires     <- Gen.posNum[Long].map(PosLong.unsafeFrom)
    } yield TokenResponse(access_token = accessToken, token_type = tokenType, expires_in = expires)
  }

}

class TokenApiGoldenTest extends AnyFlatSpec with FlatSpecDiscipline with Configuration with ArbitraryInstances {
  import TokenApiGoldenTest._
  import enumeratum.scalacheck._

  implicit final val eqTokenType: Eq[TokenType]         = Eq.fromUniversalEquals
  implicit final val eqToken: Eq[Token]                 = Eq.fromUniversalEquals
  implicit final val eqTokenResponse: Eq[TokenResponse] = Eq.fromUniversalEquals

  checkAll("GoldenCodec[TokenType]", GoldenCodecTests[TokenType].unserializableGoldenCodec)
  checkAll("GoldenCodec[Token]", GoldenCodecTests[Token].unserializableGoldenCodec)
  checkAll("GoldenCodec[TokenResponse]", GoldenCodecTests[TokenResponse].unserializableGoldenCodec)

}
