package com.gu.acquisitionsValueCalculatorClient.model

import cats.syntax.either._
import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor}

sealed trait AnnualisedValueResult

object AnnualisedValueResult {

  implicit val annualisedValueResultDecoder: Decoder[AnnualisedValueResult] = {
    val annualisedValueTwoDecoder: Decoder[AnnualisedValueResult] = new Decoder[AnnualisedValueResult] {
      override def apply(c: HCursor): Result[AnnualisedValueResult] =
        for {
          annualisedValueTwo <- c.downField("AnnualisedValueTwo").as[HCursor]
          amount <- annualisedValueTwo.downField("amount").as[Double]
        } yield {
          AnnualisedValueTwo(amount)
        }
    }
    val avErrorDecoder: Decoder[AnnualisedValueResult] = new Decoder[AnnualisedValueResult] {
      override def apply(c: HCursor): Result[AnnualisedValueResult] =
        for {
          avError <- c.downField("AVError").as[HCursor]
          error <- avError.downField("error").as[String]
        } yield {
          AVError(error)
        }
    }
    annualisedValueTwoDecoder.or(avErrorDecoder)
  }

  def fromResult(result: Either[String, Double]): AnnualisedValueResult = {
    result.fold(
      err => AVError(err),
      value => AnnualisedValueTwo(value)
    )
  }
}

case class AnnualisedValueTwo(amount: Double) extends AnnualisedValueResult

case class AVError(error: String) extends AnnualisedValueResult
