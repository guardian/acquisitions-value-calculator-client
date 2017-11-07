package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.retry.{PredefinedRetryPolicies, RetryPolicy}
import com.amazonaws.services.lambda.{AWSLambda, AWSLambdaClient, AWSLambdaClientBuilder}

object AnnualisedValueClient {

  val getRegion =  Region getRegion Regions.EU_WEST_1

  val clientConfig = new ClientConfiguration().withRetryPolicy(
    new RetryPolicy(
      PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
      PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY,
      20,
      false
    )
  )
  def createLambdaClient(creds: AWSCredentialsProvider): AWSLambda = {
    getRegion.createClient(classOf[AWSLambdaClient], creds, null)
   //AWSLambdaClientBuilder.standard().withRegion(region).withCredentials(creds).withClientConfiguration(clientConfig).build()
  }
}