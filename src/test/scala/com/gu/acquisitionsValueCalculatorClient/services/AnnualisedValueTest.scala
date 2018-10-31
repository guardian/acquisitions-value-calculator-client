package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.{AcquisitionModel, PrintOptionsModel}
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import org.scalatest._
import org.scalatest.concurrent.Eventually

class AnnualisedValueTest extends AsyncFlatSpec with Matchers with OptionValues with Eventually {

  behavior of "av service"

  val acquisition = AcquisitionModel(50, "PRINT_SUBSCRIPTION", "GBP", "ONE_OFF", Some("STRIPE"), Some(PrintOptionsModel("VOUCHER_WEEKEND_PLUS", "GB")))
  ignore should "succesfully return AcquisitionModel given valid input -  no payment provider" in {
    AnnualisedValueService.getAsyncAV(acquisition, "ophan").map(_ should be ('right))
  }
}
