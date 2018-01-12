package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.Json
import io.circe.syntax._
import org.scalatest.{EitherValues, FlatSpec, Matchers}

class AcquisitionModelTest extends FlatSpec with Matchers with EitherValues {

  behavior of "an acquisition model"

  val acquisitionModel = AcquisitionModel(
    amount = 10.0,
    product = "SUBSCRIPTION",
    currency = "GBP",
    paymentFrequency = "ONE_OFF",
    paymentProvider = Some("STRIPE"),
    printOptions = Some(
      PrintOptionsModel(
        product = "SUBSCRIPTION",
        deliveryCountryCode = "UK"
      )
    )
  )

  val json: Json = Json.obj(
    "amount" -> Json.fromDoubleOrNull(10.0),
    "product" -> Json.fromString("SUBSCRIPTION"),
    "currency" -> Json.fromString("GBP"),
    "paymentFrequency" -> Json.fromString("ONE_OFF"),
    "paymentProvider" -> Json.fromString("STRIPE"),
    "printOptions" -> Json.obj(
      "product" -> Json.fromString("SUBSCRIPTION"),
      "deliveryCountryCode" -> Json.fromString("UK")
    )
  )

  it should "be able to be decoded from JSON" in {
    json.as[AcquisitionModel].right.value shouldEqual acquisitionModel
  }

  it should "be able to be encoded to JSON" in {
    acquisitionModel.asJson shouldEqual json
  }

  val acquisitionModelMissingFields = AcquisitionModel(
    amount = 10.0,
    product = "SUBSCRIPTION",
    currency = "GBP",
    paymentFrequency = "ONE_OFF",
    paymentProvider = None,
    printOptions = None
  )

  val jsonMissingFields: Json = Json.obj(
    "amount" -> Json.fromDoubleOrNull(10.0),
    "product" -> Json.fromString("SUBSCRIPTION"),
    "currency" -> Json.fromString("GBP"),
    "paymentFrequency" -> Json.fromString("ONE_OFF")
  )

  it should "be able to be decoded from JSON when the optional fields are missing" in {
    jsonMissingFields.as[AcquisitionModel].right.value shouldEqual acquisitionModelMissingFields
  }

  it should "be able to be encoded to JSON when the optional fields are missing" in {
    acquisitionModelMissingFields.asJson shouldEqual jsonMissingFields
  }
}
