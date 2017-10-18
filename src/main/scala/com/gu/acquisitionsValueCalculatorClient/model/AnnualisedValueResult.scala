package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._



sealed trait AnnualisedValueResult

object AnnualisedValueResult {
  implicit val responseBodyDecoder: Decoder[AnnualisedValueResult] = deriveDecoder
  implicit val responseBodyEncoder: Encoder[AnnualisedValueResult] = deriveEncoder

  def fromResult(result: Either[String, Double]): AnnualisedValueResult = {
    result.fold(
      err => AVError(err),
      value => AnnualisedValueTwo(value)
    )
  }
}

case class AnnualisedValueTwo(amount: Double) extends AnnualisedValueResult
case class AVError(error: String) extends AnnualisedValueResult

