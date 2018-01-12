package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.Json
import io.circe.syntax._
import org.scalatest.{EitherValues, FlatSpec, Matchers}

class PrintOptionsModelTest extends FlatSpec with Matchers with EitherValues {

  behavior of "a print options model"

  val json: Json = Json.obj(
    "product" -> Json.fromString("CONTRIBUTION"),
    "deliveryCountryCode" -> Json.fromString("US")
  )

  val printOptionsModel = PrintOptionsModel(
    product = "CONTRIBUTION",
    deliveryCountryCode = "US"
  )

  it should "be able to be decoded from JSON" in {
    json.as[PrintOptionsModel].right.value shouldEqual printOptionsModel
  }

  it should "be able to be encode to JSON" in {
    printOptionsModel.asJson shouldEqual json
  }
}
