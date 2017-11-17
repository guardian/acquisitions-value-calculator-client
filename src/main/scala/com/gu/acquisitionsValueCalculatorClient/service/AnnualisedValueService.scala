package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.services.lambda.model.InvokeRequest
import com.gu.acquisitionsValueCalculatorClient.model.{AVError, AcquisitionModel, AnnualisedValueResult, AnnualisedValueTwo}
import io.circe.syntax._
import io.circe.parser._
import cats.syntax.either._
import com.gu.acquisitionsValueCalculatorClient.utils.ProfileAwareCredentialsProviderChain

object AnnualisedValueService extends AnnualisedValueService

class AnnualisedValueService {

  def annualisedValueResultFromJson(json: String): Either[String, AnnualisedValueResult] = {
    decode[AnnualisedValueResult](json).leftMap(e => "Error: Unable to parse ( " + json + "). " + e.getMessage)
  }

  def getAV(acquisitionModel: AcquisitionModel, accountName: String): Either[String, Double] = {

    implicit val lambda = AnnualisedValueClient.createLambdaClient(new ProfileAwareCredentialsProviderChain(accountName))

    val invokeRequest = new InvokeRequest
    invokeRequest.setFunctionName("acquisitions-value-calculator-PROD")
    invokeRequest.setPayload(acquisitionModel.asJson.noSpaces)
    val response = new String(lambda.invoke(invokeRequest).getPayload.array())
    annualisedValueResultFromJson(response).flatMap{
      case AVError(e) => Left(e)
      case AnnualisedValueTwo(amount) => Right(amount)
    }
  }
}
