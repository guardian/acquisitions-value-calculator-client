import ReleaseTransformations._

name := "acquisitions-value-calculator-service"
organization := "com.gu"

description:= "A Client/Service for the acquisitions value calculator"

version := "1.0"

scalaVersion := "2.11.11"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-target:jvm-1.8",
  "-Ywarn-dead-code"
)

resolvers += Resolver.bintrayRepo("guardian", "ophan")

val circeVersion = "0.7.0"

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/acquisitions-value-calculator-client"),
  "scm:git:git@github.com:guardian/acquisitions-value-calculator-client.git"
))


publishTo :=
  Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.amazonaws" % "aws-java-sdk" % "1.11.77",
  "org.typelevel" %% "cats-core" % "1.0.0-MF",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.gu" %% "ophan-event-model" % "1.0.0",
  "com.gu" %% "fezziwig" % "0.6",
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-generic-extras_sjs0.6" % circeVersion
    exclude("org.typelevel", "cats-core_sjs0.6_2.11" )
    exclude("com.chuusai", "shapeless_sjs0.6_2.11")
)

releaseCrossBuild := true // true if you cross-build the project for multiple Scala versions
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)