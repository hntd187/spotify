import org.openqa.selenium.chrome.ChromeOptions
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val scalalibraryVersion = "2.13.3"
lazy val scalatestVersion    = "3.2.2"
lazy val sttpVersion         = "2.2.9"
lazy val circeVersion        = "0.13.0"
lazy val scribeVersion       = "2.8.3"

scalaVersion                  := scalalibraryVersion
ThisBuild / turbo             := true
publish / skip                := true
Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / githubOwner       := "hntd187"
ThisBuild / githubRepository  := "spotify"
ThisBuild / githubTokenSource := TokenSource.GitConfig("github.token")

lazy val browserTestSettings = Seq(
  jsEnv in Test := {
    val debugging = false
    val options = new ChromeOptions()
      .addArguments("auto-open-devtools-for-tabs", "disable-web-security")
      .setHeadless(!debugging)
    new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(debugging))
  }
)

lazy val common = Seq(
  name                           := "spotify-api",
  organization                   := "io.scarman",
  scalafmtOnCompile in ThisBuild := true,
  parallelExecution in Test      := false,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://www.github.com/hntd187/spotify")),
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

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .withoutSuffixFor(JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(common)
  .settings(
    scalaVersion := scalalibraryVersion,
    crossScalaVersions ++= Seq("2.13.2", "2.12.11"),
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %%% "core"                            % sttpVersion,
      "com.softwaremill.sttp.client" %% "async-http-client-backend-future" % sttpVersion,
      "com.softwaremill.sttp.client" %%% "circe"                           % sttpVersion,
      "com.outr"                     %%% "scribe"                          % scribeVersion,
      "io.circe"                     %%% "circe-core"                      % circeVersion,
      "io.circe"                     %%% "circe-parser"                    % circeVersion,
      "io.circe"                     %%% "circe-generic"                   % circeVersion,
      "io.circe"                     %%% "circe-generic-extras"            % circeVersion,
      "org.scalatest"                %%% "scalatest"                       % scalatestVersion % Test,
      "io.github.cquiroz"            %%% "scala-java-time"                 % "2.0.0"
    )
  )
  .jsSettings(browserTestSettings)
  .jsSettings(
    coverageEnabled := false,
    scalaVersion    := scalalibraryVersion
  )

lazy val example = project
  .in(file("example-scalajs-app"))
  .settings(common)
  .settings(
    name         := "example-app",
    scalaVersion := scalalibraryVersion,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    mainClass in Compile := Some("io.scarman.spotify.ExampleApp"),
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.9.1"
    ),
    publish / skip := true,
    test in Test   := {}
//    Disabled for now until updated
//    localUrl := ("localhost", 8080),
//    workbenchDefaultRootObject := Some(("example-scalajs-app/", "example-scalajs-app/")),
//    workbenchStartMode := WorkbenchStartModes.Manual
  )
//  .enablePlugins(WorkbenchPlugin)
  .dependsOn(core.js)
