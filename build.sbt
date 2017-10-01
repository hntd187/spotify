name := "spotify-api"

organization := "io.scarman"

version := "0.1"

scalaVersion := "2.12.3"

scalacOptions ++= Seq(
  "-feature",
  "-encoding",
  "utf-8",
  "-explaintypes",
  "-language:postfixOps",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)

lazy val dispatchVersion  = "0.13.2"
lazy val log4jVersion     = "2.9.1"
lazy val json4sVersion    = "3.5.3"
lazy val scalatestVersion = "3.0.4"

libraryDependencies ++= Seq(
  "net.databinder.dispatch"  %% "dispatch-core"          % dispatchVersion,
  "net.databinder.dispatch"  %% "dispatch-json4s-native" % dispatchVersion,
  "org.json4s"               %% "json4s-jackson"         % json4sVersion,
  "org.apache.logging.log4j" % "log4j-core"              % log4jVersion,
  "org.apache.logging.log4j" % "log4j-api"               % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl"        % log4jVersion,
  "org.scalatest"            %% "scalatest"              % scalatestVersion % Test
)
