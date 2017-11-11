name := "spotify-api"

organization := "io.scarman"

scalaVersion := "2.12.4"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://www.github.com/hntd187/spotify"))

bintrayPackageLabels := Seq("spotify", "music")

crossScalaVersions := Seq("2.11.11", "2.12.4")

scalafmtOnCompile in ThisBuild := true

scalacOptions ++= Seq(
  "-feature",
  "-encoding",
  "utf-8",
  "-deprecation",
  "-explaintypes",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-unchecked"
)

lazy val dispatchVersion  = "0.13.2"
lazy val log4jVersion     = "2.9.1"
lazy val scalatestVersion = "3.0.4"
lazy val nettyVersion     = "4.0.51.Final"

libraryDependencies ++= Seq(
  "net.databinder.dispatch"  %% "dispatch-core"           % dispatchVersion exclude ("io.netty", "netty-handler"),
  "net.databinder.dispatch"  %% "dispatch-json4s-jackson" % dispatchVersion,
  "org.apache.logging.log4j" % "log4j-core"               % log4jVersion,
  "org.apache.logging.log4j" % "log4j-api"                % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl"         % log4jVersion,
  "io.netty"                 % "netty-handler"            % nettyVersion,
  "org.scalatest"            %% "scalatest"               % scalatestVersion % Test
)
