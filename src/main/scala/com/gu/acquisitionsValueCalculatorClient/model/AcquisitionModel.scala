package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class AcquisitionModel(amount: Double, product: String, currency: String, paymentFrequency: String, paymentProvider: Option[String])

object AcquisitionModel {
  implicit val acquisitionEncode: Encoder[AcquisitionModel] = deriveEncoder

  implicit val acquisitionDecode: Decoder[AcquisitionModel] = deriveDecoder
}