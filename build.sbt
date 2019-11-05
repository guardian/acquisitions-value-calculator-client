import ReleaseTransformations._
import xerial.sbt.Sonatype.autoImport.sonatypePublishToBundle

name := "acquisitions-value-calculator-client"
organization := "com.gu"

description:= "A Client/Service for the acquisitions value calculator"

scalaVersion := "2.13.1"

crossScalaVersions := Seq(scalaVersion.value, "2.12.10")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-target:jvm-1.8",
  "-Ywarn-dead-code"
)

resolvers += Resolver.bintrayRepo("guardian", "ophan")
resolvers += Resolver.sonatypeRepo("releases")

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/acquisitions-value-calculator-client"),
  "scm:git:git@github.com:guardian/acquisitions-value-calculator-client.git"
))

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra := (
  <url>https://github.com/guardian/acquisitions-value-calculator-client</url>
    <developers>
      <developer>
        <id>jranks123</id>
        <name>Jonathan Rankin</name>
        <url>https://github.com/guardian</url>
      </developer>
      <developer>
        <id>desbo</id>
        <name>Sam Desborough</name>
        <url>https://github.com/desbo</url>
      </developer>
    </developers>
  )

isSnapshot := false

publishTo := sonatypePublishToBundle.value

val circeVersion = "0.12.2"
val awsVersion = "1.11.77"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "com.amazonaws" % "aws-java-sdk-core" % awsVersion,
  "com.amazonaws" % "aws-java-sdk-lambda" % awsVersion,
  "com.amazonaws" %  "aws-java-sdk-sts" % awsVersion,
  "com.typesafe" % "config" % "1.3.1",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.gu" %% "fezziwig" % "1.3",
  "io.circe" %% "circe-parser" % circeVersion
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
  // For non cross-build projects, use releaseStepCommand("publishSigned")
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
