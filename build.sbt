ThisBuild / organization := "com.guizmaii"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / scalafmtOnCompile := true
ThisBuild / scalafmtCheck := true
ThisBuild / scalafmtSbtCheck := true

//// Dependencies
lazy val catsEffect      = "org.typelevel" %% "cats-effect"      % "2.1.2"
lazy val enumeratum      = "com.beachape"  %% "enumeratum"       % "1.5.14"
lazy val enumeratumCirce = "com.beachape"  %% "enumeratum-circe" % "1.5.23"

lazy val refined = (
  (version: String) =>
    Seq(
      "eu.timepit" %% "refined"            % version,
      "eu.timepit" %% "refined-cats"       % version,
      "eu.timepit" %% "refined-pureconfig" % version
    )
)("0.9.13")

lazy val circe = (
  (version: String) =>
    Seq(
      "io.circe" %% "circe-core"           % version,
      "io.circe" %% "circe-generic"        % version,
      "io.circe" %% "circe-generic-extras" % version,
      "io.circe" %% "circe-parser"         % version,
      "io.circe" %% "circe-refined"        % version,
      "io.circe" %% "circe-optics"         % version,
      "io.circe" %% "circe-literal"        % version
    )
)("0.13.0")

lazy val testKit = {
  Seq(
    "org.scalacheck"    %% "scalacheck"               % "1.14.3",
    "org.scalatest"     %% "scalatest"                % "3.1.1",
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2",
    "com.beachape"      %% "enumeratum-scalacheck"    % "1.5.16",
    "io.circe"          %% "circe-golden"             % "0.2.1",
    "eu.timepit"        %% "refined-scalacheck"       % "0.9.13"
  )
}.map(_ % Test)
//// Modules

lazy val root = Project(id = "basiq-io-scala-client", base = file("."))
  .settings(moduleName := "root")
  .settings(noPublishSettings)
  .aggregate(core)
  .dependsOn(core)

lazy val core = project
  .settings(moduleName := "core")
  .settings(libraryDependencies ++= Seq(catsEffect, enumeratum, enumeratumCirce) ++ circe ++ refined ++ testKit)

//// Publishing settings

/**
 * Copied from Cats
 */
lazy val noPublishSettings =
  Seq(publish := {}, publishLocal := {}, publishArtifact := false)
