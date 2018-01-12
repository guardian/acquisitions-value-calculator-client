package com.gu.acquisitionsValueCalculatorClient.model

import cats.syntax.either._
import io.circe.Decoder.Result
import io.circe._
import io.circe.syntax._

case class PrintOptionsModel(product: String, deliveryCountryCode: String)

object PrintOptionsModel {

  // Derived manually to circumvent issues that macro derived encoders and decoders where causing
  // when this package was used in a Spark job.
  implicit val printOptionsModelDecoder: Decoder[PrintOptionsModel] = new Decoder[PrintOptionsModel] {
    override def apply(c: HCursor): Result[PrintOptionsModel] =
      for {
        product <- c.downField("product").as[String]
        deliveryCountryCode <- c.downField("deliveryCountryCode").as[String]
      } yield {
        PrintOptionsModel(product, deliveryCountryCode)
      }
  }

  // Derived manually to circumvent issues that macro derived encoders and decoders where causing
  // when this package was used in a Spark job.
  implicit val printOptionsModelEncoder: Encoder[PrintOptionsModel] = new Encoder[PrintOptionsModel] {
    override def apply(a: PrintOptionsModel): Json = Json.obj(
      "product" -> Json.fromString(a.product),
      "deliveryCountryCode" -> Json.fromString(a.deliveryCountryCode)
    )
  }
}

case class AcquisitionModel(
  amount: Double,
  product: String,
  currency: String,
  paymentFrequency: String,
  paymentProvider: Option[String],
  printOptions: Option[PrintOptionsModel])

object AcquisitionModel {

  // Derived manually to circumvent issues that macro derived encoders and decoders where causing
  // when this package was used in a Spark job.
  implicit val acquisitionModelDecoder: Decoder[AcquisitionModel] = new Decoder[AcquisitionModel] {
    override def apply(c: HCursor): Result[AcquisitionModel] =
      for {
        amount <- c.downField("amount").as[Double]
        product <- c.downField("product").as[String]
        currency <- c.downField("currency").as[String]
        paymentFrequency <- c.downField("paymentFrequency").as[String]
        paymentProvider <- c.downField("paymentProvider").as[Option[String]]
        printOptions <- c.downField("printOptions").as[Option[PrintOptionsModel]]
      } yield {
        AcquisitionModel(
          amount,
          product,
          currency,
          paymentFrequency,
          paymentProvider,
          printOptions
        )
      }
  }

  // Derived manually to circumvent issues that macro derived encoders and decoders where causing
  // when this package was used in a Spark job.
  implicit val acquisitionModelEncoder: Encoder[AcquisitionModel] = new Encoder[AcquisitionModel] {
    override def apply(a: AcquisitionModel): Json = {
      var map = Map(
        "amount" -> Json.fromDoubleOrNull(a.amount),
        "product" -> Json.fromString(a.product),
        "currency" -> Json.fromString(a.currency),
        "paymentFrequency" -> Json.fromString(a.paymentFrequency)
      )
      a.paymentProvider.foreach(paymentProvider => map += ("paymentProvider" -> Json.fromString(paymentProvider)))
      a.printOptions.foreach(printOptions => map += ("printOptions" -> printOptions.asJson))
      JsonObject.fromMap(map).asJson
    }
  }
}