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

  def fromPrimitives(amount: Double, product: String, currency: String, paymentFrequency: String, paymentProvider: Option[String]): Either[String, AcquisitionModel] = {
    import cats.syntax.either._
    type R[A] = Either[String, Option[A]]

    def parseOptionalEnum [A] (value: Option[String], valueOf: String => Option[A]) : R[A] = {
      value.map(v => valueOf(v)).fold[R[A]](Right(None)) {
        case None => Left("")
        case a => Right(a)
      }
    }

    for {
      product <- Either.fromOption(Product.valueOf(product), s"Error: $product is not a valid product")
      paymentFrequency <- Either.fromOption(PaymentFrequency.valueOf(paymentFrequency), "Error: could not parse product")
      paymentProvider <- parseOptionalEnum(paymentProvider, PaymentProvider.valueOf)
    } yield {
      AcquisitionModel(amount, product, currency, paymentFrequency, paymentProvider)
    }
  }

}