package com.gu.acquisitionsValueCalculatorClient.utils

import com.amazonaws.auth._
import com.amazonaws.auth.profile.ProfileCredentialsProvider

case class ProfileAwareCredentialsProviderChain(profile: String) extends AWSCredentialsProvider {
  val profileCredentialsProvider = new ProfileCredentialsProvider(profile)

  val chain = new AWSCredentialsProviderChain(
    new EnvironmentVariableCredentialsProvider,
    new SystemPropertiesCredentialsProvider,
    profileCredentialsProvider,
    new InstanceProfileCredentialsProvider(false),
    new STSAssumeRoleSessionCredentialsProvider.Builder("arn:aws:iam::021353022223:role/support-invoke-value-calculator", "invokeLambda").build()
  )

  override def refresh(): Unit = chain.refresh()
  override def getCredentials: AWSCredentials = chain.getCredentials
}
