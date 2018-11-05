package com.gu.acquisitionsValueCalculatorClient.utils

import com.typesafe.config.ConfigFactory

object Configuration {
  private val config =  ConfigFactory.load()

  def lambdaArn = config.getString("AVCalculator.lambdaArn")
  def roleArn = config.getString("AVCalculator.roleArn")
}
