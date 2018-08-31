import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

name := "spotify-api"
lazy val scalatestVersion = "3.0.5"

lazy val common = Seq(
  name := "spotify-api",
  organization := "io.scarman",
  scalaVersion := "2.12.6",
  scalafmtOnCompile in ThisBuild := true,
  parallelExecution in Test := true,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://www.github.com/hntd187/spotify")),
  bintrayPackageLabels := Seq("spotify", "music"),
  crossScalaVersions := Seq("2.11.11", "2.12.6"),
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
)

//lazy val root = project
//  .aggregate(coreJvm, coreJs)
//  .in(file("."))
//  .settings(
//    name := "spotify-api",
//    publish := {},
//    publishLocal := {}
//  )
lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      //"fr.hmil"       %%% "roshttp"       % "2.1.0",
      "com.softwaremill.sttp" %%% "core"          % "1.3.0",
      "com.softwaremill.sttp" %%% "circe"         % "1.3.0",
      "com.softwaremill.sttp" %% "async-http-client-backend-future" % "1.3.0",
      "com.outr"              %%% "scribe"        % "2.5.3",
      "io.circe"              %%% "circe-core"    % "0.9.3",
      "io.circe"              %%% "circe-parser"  % "0.9.3",
      "io.circe"              %%% "circe-generic" % "0.9.3",
      "org.scalatest"         %%% "scalatest"     % scalatestVersion % Test
    )
  )
  .jvmSettings()
  .jsSettings()

//lazy val coreJvm = core.jvm
//lazy val coreJs  = core.js
