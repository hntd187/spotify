import org.openqa.selenium.chrome.ChromeOptions
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

//name := "spotify-api"

lazy val scalatestVersion = "3.0.5"
lazy val sttpVersion      = "1.3.1"
lazy val circeVersion     = "0.9.3"
lazy val scribeVersion    = "2.6.0"

lazy val browserTestSettings = Seq(
  jsEnv in Test := {
    val debugging = false

    val options = new ChromeOptions().addArguments(
      "auto-open-devtools-for-tabs",
      "disable-web-security",
      if (debugging) "" else "headless"
    )

    new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(debugging))
  }
)

lazy val common = Seq(
  name := "spotify-api",
  organization := "io.scarman",
  scalaVersion := "2.12.6",
  scalafmtOnCompile in ThisBuild := true,
  parallelExecution in Test := false,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://www.github.com/hntd187/spotify")),
  bintrayPackageLabels := Seq("spotify", "music"),
  crossScalaVersions := Seq("2.11.12", "2.12.6"),
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
