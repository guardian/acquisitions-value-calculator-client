package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.AcquisitionModel
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import ophan.thrift.event.{PaymentFrequency, PaymentProvider, Product}
import org.scalatest.concurrent.Eventually
import org.scalatest.{Matchers, OptionValues}

//For testing locally that the lambda is being called correctly. Run with "sbt test:run"
object LambdaRunner extends App with Matchers with OptionValues with Eventually {
  val acquisition = AcquisitionModel(50, Product(1), "GBP", PaymentFrequency(1), Some(PaymentProvider(1)))
  AnnualisedValueService.getAV(acquisition, "ophan") should be ('right)
}
