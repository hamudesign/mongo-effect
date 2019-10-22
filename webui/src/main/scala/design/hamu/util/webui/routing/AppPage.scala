package design.hamu.util.webui.routing

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

sealed trait AppPage

case object Home extends AppPage {
  val component = ScalaComponent
    .builder[Unit]("HomePage")
    .renderStatic(
      <.div(^.className := "mb-5")(
        <.div(^.className := "jumbotron")(
          <.div(
            ^.className := "d-flex justify-content-center align-items-center"
          )(
            <.img(
              ^.className := "d-inline-block",
              ^.src := "./img/mongodb.png",
              ^.height := "100"
            ),
            <.h5(^.className := "display-4 ml-5 mr-5")("+"),
            <.img(
              ^.className := "d-inline-block",
              ^.src := "./img/fs2.png",
              ^.height := "100"
            )
          )
        ),
        <.div(^.className := "container")(
          <.div(^.className := "mb-5")(
            <.h4(^.className := "display-4")("Mongo Scala Driver & FS2"),
            <.div(
              <.a(^.className := "mr-3", ^.href := "https://index.scala-lang.org/hamuhouse/mongo-effect/mongo-effect")(
                <.img(^.src := "https://img.shields.io/maven-central/v/design.hamu/mongo-effect_2.12"),
              ),
              <.a(^.href := "https://javadoc.io/doc/design.hamu/mongo-effect_2.12")(
                <.img(^.src := "https://javadoc.io/badge2/design.hamu/mongo-effect_2.12/javadoc.io.svg"),
              )
            )
          ),
          <.div(
            <.h5(^.className := "display-5")("Getting Started"),
            <.p(^.className := "text-muted")(
              "To get started, include the following in your build.sbt:"
            ),
            <.pre(^.className := "text-muted bg-light p-5")(
              <.code(
                "libraryDependencies += \"co.fs2\" %% \"fs2-core\" % fs2Version"
              ),
              <.br(),
              <.code(
                "libraryDependencies += \"co.fs2\" %% \"fs2-reactive-streams\" % fs2Version"
              ),
              <.br(),
              <.code(
                "libraryDependencies += \"org.mongodb.scala\" %% \"mongo-scala-driver\" %% mongoDriverVersion"
              ),
              <.br(),
              <.code(
                "libraryDependencies += \"design.hamu\" %% \"mongo-effect\" %% mongoEffectVersion"
              )
            )
          ),
          <.div(
            <.h5(^.className := "display-5")("Example Usage"),
            <.p(^.className := "text-muted")(
              "Get a stream of gmail users in alphabetical order by last name"
            ),
            <.pre(^.className := "bg-light text-muted p-5")(
              <.code(
                "val collection: Collection[User] = database.getCollection(\"users\")"
              ),
              <.br(),
              <.code("val gmailUsers: Stream[IO, User] = collection"),
              <.br(),
              <.code("   .find(regex(\"email\", \"^.*@gmail.com$\")"),
              <.br(),
              <.code("   .sort(ascending(\"lastName\"))"),
              <.br(),
              <.code("   .toStream[IO]")
            )
          )
        )
      )
    )
    .build
}

case object Guide extends AppPage {
  val component = ScalaComponent
    .builder[Unit]("GuidePage")
    .renderStatic(
      <.div(^.className := "container mt-5 mb-5")(
        <.div(
          <.h4(^.className := "display-4")("Mongo-effect Guide"),
          <.p(^.className := "text-muted")(
            """
            Below are all use cases you will need to integrate mongo db into your fs2 application.
          """
          )
        ),
        <.div(
          <.h5(^.className := "display-5")("Accessors"),
          <.span(^.className := "text-muted")(
            "Extract head from mongo observable:"
          ),
          <.pre(^.className := "bg-light text-muted p-5")(
            <.code("val obs: Observable[T]"),
            <.br(),
            <.code("val head: T = obs.headF[IO].unsafeRunSync")
          ),
          <.span(^.className := "text-muted")(
            "Extract head as option from observable:"
          ),
          <.pre(^.className := "bg-light text-muted p-5")(
            <.code("val obs: Observable[T]"),
            <.br(),
            <.code("val head: Option[T] = obs.headOptF[IO].unsafeRunSync")
          )
        ),
        <.div(
          <.h5(^.className := "display-5")("Stream conversion"),
          <.span(^.className := "text-muted")(
            "Convert mongo observable into fs2 stream:"
          ),
          <.pre(^.className := "bg-light text-muted p-5")(
            <.code("val obs: Observable[T]"),
            <.br(),
            <.code("val stream: Stream[IO,T] = obs.toStream[IO]")
          )
        )
      )
    )
    .build
}
