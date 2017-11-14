package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class PrintOptionsModel(PrintProduct: String, deliveryCountryCode: String)

case class AcquisitionModel(
  amount: Double,
  product: String,
  currency: String,
  paymentFrequency: String,
  paymentProvider: Option[String],
  printOptionsModel: Option[PrintOptionsModel])

object AcquisitionModel {
  implicit val acquisitionEncode: Encoder[AcquisitionModel] = deriveEncoder

  implicit val acquisitionDecode: Decoder[AcquisitionModel] = deriveDecoder
}