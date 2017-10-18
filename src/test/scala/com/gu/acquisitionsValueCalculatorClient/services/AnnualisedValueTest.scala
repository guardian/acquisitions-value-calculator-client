package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.{AcquisitionModel, AnnualisedValueTwo}
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import ophan.thrift.event.{PaymentFrequency, PaymentProvider, Product}
import org.scalatest._
import org.scalatest.concurrent.Eventually

class AnnualisedValueTest extends FlatSpec with Matchers with OptionValues with Eventually {

  behavior of "AV service"

  it should "get the coprrect AV" in {
    val acquisition = AcquisitionModel(50, Product(1), "GBP", PaymentFrequency(1), Some(PaymentProvider(1)))
    AnnualisedValueService.getAV(acquisition, "ophan") shouldBe Right(AnnualisedValueTwo(49.5))
  }
}
