import org.openqa.selenium.chrome.ChromeOptions
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val scalalibraryVersion = "2.12.8"
lazy val scalatestVersion    = "3.0.8"
lazy val sttpVersion         = "1.6.1"
lazy val circeVersion        = "0.12.0-M3"
lazy val scribeVersion       = "2.7.8"

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
      "org.scalatest"         %%% "scalatest"                       % scalatestVersion % Test,
      "io.github.cquiroz"     %%% "scala-java-time"                 % "2.0.0-RC3"
    )
  )
  .jsSettings(browserTestSettings)
  .jsSettings(coverageEnabled := false)
  .jsSettings(scalacOptions += "-P:scalajs:sjsDefinedByDefault")

lazy val example = project
  .in(file("example-scalajs-app"))
  .settings(common)
  .settings(
    name := "example-app",
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= { _.withESFeatures(_.withUseECMAScript2015(true)) },
    mainClass in Compile := Some("io.scarman.spotify.ExampleApp"),
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "com.lihaoyi"       %%% "scalatags"       % "0.6.8"
    ),
    jsEnv := {
      val options =
        new ChromeOptions().addArguments("auto-open-devtools-for-tabs", "disable-web-security", "start-maximized").setHeadless(true)
      new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(true))
    },
    artifactPath in (Compile, fastOptJS) := file("C:\\Users\\Stephen Carman\\software\\nginx-1.15.9\\html\\app-example.js")
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(core.js)
