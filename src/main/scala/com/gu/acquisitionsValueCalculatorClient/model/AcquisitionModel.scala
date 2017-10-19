package com.gu.acquisitionsValueCalculatorClient.model

import com.gu.fezziwig.CirceScroogeMacros._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import ophan.thrift.event._
import scala.reflect.ClassTag

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

    def error[A: ClassTag](value: String, classTag: ClassTag[A]): String = {
      s"Error: $value is not a valid $classTag"
    }

    def toCamel(value: String): String = value.replaceAll("_", "")

    def parseOptionalEnum [A: ClassTag](value: Option[String], valueOf: String => Option[A]) : R[A] = {
      value.map(v => valueOf(toCamel(v))).fold[R[A]](Right(None)) {
        case None => Left(error(value.getOrElse(""), reflect.classTag[A]))
        case a => Right(a)
      }
    }

    def parseEnum[A: ClassTag](value: String, valueOf: String => Option[A]): Either[String, A] = {
      Either.fromOption(valueOf(toCamel(value)), error(value, reflect.classTag[A]))
    }

    for {
      product <- parseEnum(product, Product.valueOf)
      paymentFrequency <- parseEnum(paymentFrequency, PaymentFrequency.valueOf)
      paymentProvider <- parseOptionalEnum(paymentProvider, PaymentProvider.valueOf)
    } yield {
      AcquisitionModel(amount, product, currency, paymentFrequency, paymentProvider)
    }
  }

}