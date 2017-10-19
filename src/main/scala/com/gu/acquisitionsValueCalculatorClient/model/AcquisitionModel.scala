package com.gu.acquisitionsValueCalculatorClient.model

import com.gu.fezziwig.CirceScroogeMacros._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import ophan.thrift.event._
import io.circe.syntax._
import io.circe.parser._
import io.circe.Error


case class AcquisitionModel(amount: Double, product: Product, currency: String, paymentFrequency: PaymentFrequency, paymentProvider: Option[PaymentProvider])
case class AcquisitionModelFromPrimitives(amount: Double, product: String, currency: String, paymentFrequency: String, paymentProvider: Option[String])
object AcquisitionModelFromPrimitives {
  implicit val acquisitionModelFromPrimitivesDecoder: Encoder[AcquisitionModelFromPrimitives] = deriveEncoder
}


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

  def fromPrimitives(amount: Double, product: String, currency: String, paymentFrequency: String, paymentProvider: Option[String]): Either[Error, AcquisitionModel] = {
    val acquistionFromPrimitives  = AcquisitionModelFromPrimitives(amount, product, currency, paymentFrequency, paymentProvider).asJson.noSpaces
    decode[AcquisitionModel](acquistionFromPrimitives)
  }

}