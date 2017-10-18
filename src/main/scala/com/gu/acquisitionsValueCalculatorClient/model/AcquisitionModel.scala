package com.gu.acquisitionsValueCalculatorClient.model

import com.gu.fezziwig.CirceScroogeMacros._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import ophan.thrift.event._

case class AcquisitionModel(amount: Double, product: Product, currency: String, paymentFrequency: PaymentFrequency, paymentProvider: Option[PaymentProvider])

object AcquisitionModel {
  implicit val acquisitionEncode: Encoder[AcquisitionModel] = {
    implicit val paymentFrequencyEncoder: Encoder[PaymentFrequency] = encodeThriftEnum
    implicit val paymentProviderEncoder: Encoder[PaymentProvider] = encodeThriftEnum
    implicit val productEncoder: Encoder[Product] = encodeThriftEnum
    deriveEncoder
  }

  implicit val acquisitionDecode: Decoder[AcquisitionModel] = {
    implicit val paymentFrequencyDecoder: Decoder[PaymentFrequency] = decodeThriftEnum
    implicit val paymentProviderDecoder: Decoder[PaymentProvider] = decodeThriftEnum
    implicit val productDecoder: Decoder[Product] = decodeThriftEnum
    deriveDecoder
  }

}