import org.openqa.selenium.chrome.ChromeOptions
import org.scalajs.jsenv.selenium.SeleniumJSEnv
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val scalalibraryVersion = "3.1.1"
lazy val scalatestVersion    = "3.2.11"
lazy val sttpVersion         = "3.5.2"
lazy val circeVersion        = "0.14.1"
lazy val scribeVersion       = "3.8.2"

scalaVersion                  := scalalibraryVersion
ThisBuild / turbo             := true
publish / skip                := true
Global / onChangedBuildSource := ReloadOnSourceChanges
githubOwner                   := "hntd187"
githubRepository              := "spotify"
githubTokenSource             := TokenSource.GitConfig("github.token")

lazy val browserTestSettings = Seq(
  Test / jsEnv := {
    val debugging = false
    val options   = new ChromeOptions()
      .addArguments("auto-open-devtools-for-tabs", "disable-web-security")
      .setHeadless(!debugging)
    new SeleniumJSEnv(options, SeleniumJSEnv.Config().withKeepAlive(debugging))
  }
)

lazy val common = Seq(
  name                     := "spotify-api",
  organization             := "io.scarman",
  Test / parallelExecution := false,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  githubTokenSource        := TokenSource.GitConfig("github.token"),
  homepage                 := Some(url("https://www.github.com/hntd187/spotify")),
  scalacOptions ++= Seq(
    "-feature",
    "-encoding",
    "utf-8",
    "-deprecation",
    "-explain",
    "-explain-types",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-unchecked",
    "-source:future-migration",
    "-rewrite"
  )
)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .withoutSuffixFor(JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(common)
  .settings(
    scalaVersion := scalalibraryVersion,
    crossScalaVersions ++= Seq(scalalibraryVersion, "2.13.8"),
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %%% "core"                             % sttpVersion,
      "com.softwaremill.sttp.client3" %%% "circe"                            % sttpVersion,
      "com.outr"                      %%% "scribe"                           % scribeVersion,
      "io.circe"                      %%% "circe-core"                       % circeVersion,
      "io.circe"                      %%% "circe-parser"                     % circeVersion,
      "io.circe"                      %%% "circe-generic"                    % circeVersion,
      "com.softwaremill.sttp.client3"  %% "async-http-client-backend-future" % sttpVersion      % Test,
      "org.scalatest"                 %%% "scalatest"                        % scalatestVersion % Test
    )
  )
  .jsSettings(browserTestSettings)
  .jsSettings(
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time"             % "2.3.0",
      "org.scala-js"      %%% "scala-js-macrotask-executor" % "1.0.0",
      ("org.scala-js"     %%% "scalajs-java-securerandom"   % "1.0.0").cross(CrossVersion.for3Use2_13)
    )
  )

lazy val example = project
  .in(file("example-scalajs-app"))
  .settings(common)
  .settings(
    name                            := "example-app",
    scalaVersion                    := scalalibraryVersion,
    Compile / mainClass             := Some("io.scarman.spotify.ExampleApp"),
    githubTokenSource               := TokenSource.GitConfig("github.token"),
    scalaJSUseMainModuleInitializer := true,
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      "com.lihaoyi"  %%% "scalatags"                   % "0.11.1",
      "org.scala-js" %%% "scala-js-macrotask-executor" % "1.0.0"
    ),
    publish / skip                  := true,
    Test / test                     := {}
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(core.js)
