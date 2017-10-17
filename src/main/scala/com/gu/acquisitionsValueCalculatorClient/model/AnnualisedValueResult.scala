package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.Decoder
import io.circe.generic.semiauto._



sealed trait AnnualisedValueResult

object AnnualisedValueResult {
  implicit val responseBodyDecoder: Decoder[AnnualisedValueResult] = deriveDecoder
}

case class AnnualisedValueTwo(amount: Double) extends AnnualisedValueResult
case class AVError(error: String) extends AnnualisedValueResult

