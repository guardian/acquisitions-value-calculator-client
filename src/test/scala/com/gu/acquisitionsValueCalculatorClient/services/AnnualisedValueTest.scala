package com.gu.acquisitionsValueCalculatorClient.services

import com.gu.acquisitionsValueCalculatorClient.model.{AcquisitionModel}
import com.gu.acquisitionsValueCalculatorClient.service.AnnualisedValueService
import ophan.thrift.event.{PaymentFrequency, PaymentProvider, Product}
import org.scalatest._
import org.scalatest.concurrent.Eventually

class AnnualisedValueTest extends FlatSpec with Matchers with OptionValues with Eventually {

  behavior of "from Primitives"


  it should "succesfully return AcquisitionModel given valid input -  no payment provider" in {
    AcquisitionModel.fromPrimitives(50, "CONTRIBUTION", "GBP", "ONE_OFF", None) should be ('right)
  }

  it should "succesfully return AcquisitionModel given valid input - valid payment provider" in {
    AcquisitionModel.fromPrimitives(50, "CONTRIBUTION", "GBP", "ONE_OFF", Some("STRIPE")) should be ('right)
  }

  it should "reject invalid input -  invalid payment provider" in {
    AcquisitionModel.fromPrimitives(50, "CONTRIBUTION", "GBP", "ONE_OFF", Some("STRIPES")) should be ('left)
  }
}
