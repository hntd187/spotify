import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

name := "spotify-api"

lazy val scalatestVersion = "3.0.5"

lazy val browserTestSettings = Seq(
  jsEnv in Test := {
    val debugging = true
    new SeleniumJSEnv(
      {
        val options = new ChromeOptions()
        val args    = Seq("auto-open-devtools-for-tabs", "disable-web-security") ++ (if (debugging) Seq.empty else Seq("headless"))
        options.addArguments(args: _*)
        val capabilities = DesiredCapabilities.chrome()
        capabilities.setCapability(ChromeOptions.CAPABILITY, options)
        capabilities
      },
      SeleniumJSEnv.Config().withKeepAlive(debugging)
    )
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

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(common)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp" %%% "core"                            % "1.3.0",
      "com.softwaremill.sttp" %%% "circe"                           % "1.3.0",
      "com.softwaremill.sttp" %% "async-http-client-backend-future" % "1.3.0",
      "com.outr"              %%% "scribe"                          % "2.6.0",
      "io.circe"              %%% "circe-core"                      % "0.9.3",
      "io.circe"              %%% "circe-parser"                    % "0.9.3",
      "io.circe"              %%% "circe-generic"                   % "0.9.3",
      "org.scalatest"         %%% "scalatest"                       % scalatestVersion % Test
    )
  )
  .jvmSettings()
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % scalatestVersion % Test
    )
  )
  .jsSettings(browserTestSettings)
