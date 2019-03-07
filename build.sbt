import org.openqa.selenium.chrome.ChromeOptions
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val scalalibraryVersion = "2.12.8"
lazy val scalatestVersion    = "3.0.5"
lazy val sttpVersion         = "1.5.11"
lazy val circeVersion        = "0.11.1"
lazy val scribeVersion       = "2.7.2"

scalaVersion := scalalibraryVersion
releaseIgnoreUntrackedFiles := true

lazy val browserTestSettings = Seq(
  jsEnv in Test := {
    val debugging = true
    val options = new ChromeOptions()
      .addArguments("auto-open-devtools-for-tabs", "disable-web-security")
      .setHeadless(!debugging)
    new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(debugging))
  }
)

lazy val common = Seq(
  name := "spotify-api",
  organization := "io.scarman",
  scalaVersion := scalalibraryVersion,
  scalafmtOnCompile in ThisBuild := true,
  parallelExecution in Test := false,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://www.github.com/hntd187/spotify")),
  bintrayPackageLabels := Seq("spotify", "music"),
  crossScalaVersions := Seq("2.11.12", scalalibraryVersion),
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
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp" %%% "core"                            % sttpVersion,
      "com.softwaremill.sttp" %%% "circe"                           % sttpVersion,
      "com.softwaremill.sttp" %% "async-http-client-backend-future" % sttpVersion,
      "com.outr"              %%% "scribe"                          % scribeVersion,
      "io.circe"              %%% "circe-core"                      % circeVersion,
      "io.circe"              %%% "circe-parser"                    % circeVersion,
      "io.circe"              %%% "circe-generic"                   % circeVersion,
      "org.scalatest"         %%% "scalatest"                       % scalatestVersion % Test
    )
  )
  .jsSettings(browserTestSettings)
  .jsSettings(coverageEnabled := false)

lazy val example = project
  .in(file("example-scalajs-app"))
  .settings(common)
  .settings(
    name := "example-app",
    scalaJSUseMainModuleInitializer := true,
    mainClass in Compile := Some("io.scarman.spotify.ExampleApp"),
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % "0.6.7"
    ),
    jsEnv := {
      val options =
        new ChromeOptions().addArguments("auto-open-devtools-for-tabs", "disable-web-security", "start-maximized").setHeadless(false)
      new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(true))
    },
    artifactPath in (Compile, fastOptJS) := file("C:\\Users\\Stephen Carman\\software\\nginx-1.15.9\\html\\app-example.js")
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(core.js)
