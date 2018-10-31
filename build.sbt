import ReleaseTransformations._

name := "acquisitions-value-calculator-client"
organization := "com.gu"

description:= "A Client/Service for the acquisitions value calculator"

scalaVersion := "2.12.4"

crossScalaVersions := Seq(scalaVersion.value, "2.11.12")

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

isSnapshot := true

publishTo :=
  Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

val circeVersion = "0.9.3"
val awsVersion = "1.11.77"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.amazonaws" % "aws-java-sdk-core" % awsVersion,
  "com.amazonaws" % "aws-java-sdk-lambda" % awsVersion,
  "com.amazonaws" %  "aws-java-sdk-sts" % awsVersion,

  "org.typelevel" %% "cats-core" % "1.0.1",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.gu" %% "fezziwig" % "0.8",
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
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
