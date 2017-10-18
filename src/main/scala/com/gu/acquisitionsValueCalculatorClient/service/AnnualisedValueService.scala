package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.model.InvokeRequest
import com.gu.acquisitionsValueCalculatorClient.model.{AcquisitionModel, AnnualisedValueResult}
import io.circe.syntax._
import io.circe.parser._
import cats.syntax.either._




object AnnualisedValueService {

  def annualisedValueResultFromJson(json: String): Either[String, AnnualisedValueResult] = {
    decode[AnnualisedValueResult](json).leftMap(e => "Error: Unable to parse ( " + json + "). " + e.getMessage)
  }

  def getAV(acquisitionModel: AcquisitionModel, accountName: String): Either[String, AnnualisedValueResult] = {

    implicit val region: Regions = AnnualisedValueClient.getRegion
    implicit val lambda = AnnualisedValueClient.createLambdaClient(region, new ProfileCredentialsProvider(accountName))

    val invokeRequest = new InvokeRequest
    invokeRequest.setFunctionName("acquisitions-value-calculator-PROD")
    invokeRequest.setPayload(acquisitionModel.asJson.noSpaces)
    val response = new String(lambda.invoke(invokeRequest).getPayload.array())
    annualisedValueResultFromJson(response)

  }


}
