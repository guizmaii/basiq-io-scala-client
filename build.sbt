ThisBuild / organization := "com.guizmaii"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / scalafmtOnCompile := true
ThisBuild / scalafmtCheck := true
ThisBuild / scalafmtSbtCheck := true

//// Dependencies

//// Modules

lazy val root = Project(id = "basiq-io-scala-client", base = file("."))
  .settings(moduleName := "root")
  .settings(noPublishSettings)
  .aggregate(core)
  .dependsOn(core)

lazy val core = project
  .settings(moduleName := "core")
  .settings(
    libraryDependencies ++= Seq()
  )

//// Publishing settings

/**
 * Copied from Cats
 */
lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)