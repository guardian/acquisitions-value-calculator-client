package com.gu.acquisitionsValueCalculatorClient.service

import com.amazonaws.services.lambda.model.{AWSLambdaException, EC2AccessDeniedException, InvocationType, InvokeRequest}
import com.gu.acquisitionsValueCalculatorClient.model.{AVError, AcquisitionModel, AnnualisedValueResult, AnnualisedValueTwo}
import io.circe.syntax._
import io.circe.parser._
import cats.syntax.either._
import com.amazonaws.SdkClientException
import com.amazonaws.services.lambda.AWSLambda
import com.gu.acquisitionsValueCalculatorClient.utils.ProfileAwareCredentialsProviderChain

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object AnnualisedValueService extends AnnualisedValueService

class AnnualisedValueService {

  def annualisedValueResultFromJson(json: String): Either[String, AnnualisedValueResult] = {
    decode[AnnualisedValueResult](json).leftMap(e => "Error: Unable to parse ( " + json + "). " + e.getMessage)
  }

  def getAV(acquisitionModel: AcquisitionModel, accountName: String)(implicit executionContext: ExecutionContext): Either[String, Double] = {

    implicit val lambda: AWSLambda = AnnualisedValueClient.createLambdaClient(ProfileAwareCredentialsProviderChain(accountName))

    val invokeRequest = new InvokeRequest
    invokeRequest.setFunctionName("acquisitions-value-calculator-PROD")
    invokeRequest.setPayload(acquisitionModel.asJson.noSpaces)

    Try(new String(lambda.invoke(invokeRequest).getPayload.array())).fold(
      error => handleError(error),
      response => annualisedValueResultFromJson(response).flatMap {
        case AVError(e) => Left(e)
        case AnnualisedValueTwo(amount) => Right(amount)
      }
    )
  }

  def handleError(error: Throwable) = Left(
    error match {
      case e: AWSLambdaException if e.getErrorCode == "ExpiredTokenException" =>
        s"Expired credentials - do you have fresh Ophan credentials from Janus? ${error.getMessage}"
      case e: SdkClientException if e.getMessage == "Unable to load AWS credentials from any provider in the chain" =>
        s"Missing credentials - do you have fresh Ophan credentials from Janus? ${error.getMessage}"
      case _ => s"Error during lambda invocation: ${error.getMessage}"
    }
  )

  def getAsyncAV(acquisitionModel: AcquisitionModel, accountName: String)(implicit executionContext: ExecutionContext): Future[Either[String, Double]] = Future {
    getAV(acquisitionModel, accountName)
  }
}
