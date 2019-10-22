name := "mongo-effect"
scalaVersion := "2.12.8"

lazy val common = Seq(
  organization := "design.hamu",
  version := "0.0.1",
  scalacOptions += "-Ypartial-unification"
)

lazy val root = project
  .in(file("."))
  .settings(common)
  .settings(
    skip in publish := true
  )
  .aggregate(core)

lazy val core = project
  .in(file("core"))
  .settings(common)
  .settings(publishSettings)
  .settings(
    name := "mongo-effect",
    libraryDependencies ++= Seq(
      Dependencies.Mongo.driver.jvm.value,
      Dependencies.Cats.core.jvm.value,
      Dependencies.Cats.effect.jvm.value,
      Dependencies.FS2.core.jvm.value,
      Dependencies.FS2.reactiveStreams.jvm.value
    ) ++ Seq(
      Dependencies.ScalaTest.core.jvm.value,
      Dependencies.ScalaMock.core.jvm.value,
      Dependencies.Cats.testEffect.jvm.value
    ).map(_ % "test")
  )

lazy val webui = project
  .in(file("webui"))
  .enablePlugins(ScalaJSPlugin)
  .settings(common)
  .settings(
    name := "webui",
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / ".." / "docs" / "js" / "index.js",
    artifactPath in (Compile, fullOptJS) := baseDirectory.value / ".." / "docs" / "js" / "index.js",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      Dependencies.ScalaJsReact.core.js.value,
      Dependencies.ScalaJsReact.extra.js.value
    )
  )

lazy val publishSettings = Seq(
  homepage := Some(url("https://hamuhouse.github.io/mongo-effect/")),
  licenses := List("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  developers := List(
    Developer(id = "matsudaskai", name = "Kai Matsuda", email = "", url = url("https://vangogh500.github.io/"))
  ),
  scmInfo := Some(
    ScmInfo(url("https://github.com/hamuhouse/mongo-effect"), "scm:git@github.com:hamuhouse/mongo-effect.git")
  ),
  publishTo := sonatypePublishTo.value
)