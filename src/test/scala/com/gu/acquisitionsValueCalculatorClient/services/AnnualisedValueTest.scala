package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.AcquisitionModel
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import org.scalatest._
import org.scalatest.concurrent.Eventually

class AnnualisedValueTest extends FlatSpec with Matchers with OptionValues with Eventually {

  behavior of "av service"

  val acquisition = AcquisitionModel(50, "CONTRIBUTION", "GBP", "ONE_OFF", Some("STRIPE"), None)
  it should "succesfully return AcquisitionModel given valid input -  no payment provider" in {
    AnnualisedValueService.getAV(acquisition, "ophan") should be ('right)

  }
}
