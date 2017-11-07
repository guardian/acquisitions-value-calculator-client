package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.AcquisitionModel
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import org.scalatest.concurrent.Eventually
import org.scalatest.{Matchers, OptionValues}

//For testing locally that the lambda is being called correctly. Run with "sbt test:run"
object LambdaRunner extends App with Matchers with OptionValues with Eventually {
  val acquisition = AcquisitionModel(50, "CONTRIBUTION", "GBP", "ONE_OFF", Some("STRIPE"))
  AnnualisedValueService.getAV(acquisition, "ophan") should be ('right)
}
