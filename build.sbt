name := "util"
organization := "design.hamu"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val common = Seq(
  scalacOptions += "-Ypartial-unification"
)

lazy val mongoeffect = project
  .in(file("mongoeffect"))
  .settings(
    name := "mongo-effect",
    common,
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
  .settings(
    name := "webui",
    common,
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / ".." / "docs" / "js" / "index.js",
    artifactPath in (Compile, fullOptJS) := baseDirectory.value / ".." / "docs" / "js" / "index.js",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      Dependencies.ScalaJsReact.core.js.value,
      Dependencies.ScalaJsReact.extra.js.value
    )
  )
