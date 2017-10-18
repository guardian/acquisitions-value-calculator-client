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

