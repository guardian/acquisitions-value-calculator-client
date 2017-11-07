package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.lambda.{AWSLambda, AWSLambdaClient }

object AnnualisedValueClient {

  val getRegion =  Region getRegion Regions.EU_WEST_1

  def createLambdaClient(creds: AWSCredentialsProvider): AWSLambda = {
    getRegion.createClient(classOf[AWSLambdaClient], creds, null)
  }
}