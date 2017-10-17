package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.retry.{PredefinedRetryPolicies, RetryPolicy}
import com.amazonaws.services.lambda.{AWSLambda, AWSLambdaClientBuilder}

object AnnualisedValueClient {

  def getRegion = Option(System.getenv("AWS_DEFAULT_REGION")).map(Regions.fromName).getOrElse(Regions.EU_WEST_1)

  val clientConfig = new ClientConfiguration().withRetryPolicy(
    new RetryPolicy(
      PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION,
      PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY,
      20,
      false
    )
  )
  def createLambdaClient(implicit region:Regions, creds: AWSCredentialsProvider): AWSLambda =
    AWSLambdaClientBuilder.standard().withRegion(region).withCredentials(creds).withClientConfiguration(clientConfig).build()
}