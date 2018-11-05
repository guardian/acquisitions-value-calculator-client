package com.gu.acquisitionsValueCalculatorClient.utils

import com.amazonaws.auth._
import com.amazonaws.auth.profile.ProfileCredentialsProvider

case class ProfileAwareCredentialsProviderChain(profile: String) extends AWSCredentialsProvider {

  val chain = new AWSCredentialsProviderChain(
    new ProfileCredentialsProvider(profile),
    new STSAssumeRoleSessionCredentialsProvider.Builder(Configuration.roleArn, "invoke-lambda").build()
  )

  override def refresh(): Unit = chain.refresh()
  override def getCredentials: AWSCredentials = chain.getCredentials
}
