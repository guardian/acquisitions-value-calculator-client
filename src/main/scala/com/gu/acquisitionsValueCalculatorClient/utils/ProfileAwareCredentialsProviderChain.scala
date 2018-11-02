package com.gu.acquisitionsValueCalculatorClient.utils

import com.amazonaws.auth._
import com.amazonaws.auth.profile.ProfileCredentialsProvider

case class ProfileAwareCredentialsProviderChain(profile: String) extends AWSCredentialsProvider {

  val roleArn = "arn:aws:iam::021353022223:role/support-invoke-value-calculator"

  val chain = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider(profile),
    new STSAssumeRoleSessionCredentialsProvider.Builder(roleArn, "invoke-lambda").build()
  )

  override def refresh(): Unit = chain.refresh()
  override def getCredentials: AWSCredentials = chain.getCredentials
}
