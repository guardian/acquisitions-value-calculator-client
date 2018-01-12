package com.gu.acquisitionsValueCalculatorClient.model

import io.circe.Json
import org.scalatest.{EitherValues, FlatSpec, Matchers}

class AnnualisedValueResultTest extends FlatSpec with Matchers with EitherValues {

  behavior of "an annualised value calculation"

  val successJson: Json = Json.obj(
    "AnnualisedValueTwo" -> Json.obj(
      "amount" -> Json.fromDoubleOrNull(10.0)
    )
  )

  val failureJson: Json = Json.obj(
    "AVError" -> Json.obj(
      "error" -> Json.fromString("the calculation failed!")
    )
  )

  it should "be able to be decoded from JSON if it was a success" in {
    successJson.as[AnnualisedValueResult].right.value shouldEqual AnnualisedValueTwo(10.0)
  }

  it should "be able to be decoded from JSON if it was a failure" in {
    failureJson.as[AnnualisedValueResult].right.value shouldEqual AVError("the calculation failed!")
  }
}
