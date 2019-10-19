package design.hamu.util.webui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Navbar {
  val component = ScalaComponent
    .builder[Unit]("Navbar")
    .renderStatic(
      <.div(^.className := "navbar navbar-expand-lg navbar-light bg-light")(
        <.div(^.className := "container")(
          <.a(^.className := "navbar-brand", ^.href := "#")(
            <.img(
              ^.className := "d-inline-block mr-3",
              ^.src := "./img/logo.png",
              ^.width := "40",
              ^.height := "40"
            ),
            "MONGO EFFECT"
          ),
          <.button(
            ^.className := "navbar-toggler",
            ^.`type` := "button",
            VdomAttr("data-toggle") := "collapse",
            VdomAttr("data-target") := "#navbar-content"
          )(
            <.span(^.className := "navbar-toggler-icon")
          ),
          <.div(
            ^.id := "navbar-content",
            ^.className := "collapse navbar-collapse"
          )(
            <.ul(^.className := "navbar-nav")(
              <.li(^.className := "nav-item")(
                <.a(^.className := "nav-link", ^.href := "#guide")("Guide")
              ),
              <.li(^.className := "nav-item")(
                <.a(^.className := "nav-link")("API")
              )
            ),
            <.ul(^.className := "navbar-nav ml-auto")(
              <.li(^.className := "nav-item")(
                <.a(
                  ^.className := "nav-link",
                  ^.href := "https://github.com/hamuhouse/mongo-effect"
                )(
                  <.i(^.className := "fab fa-github")
                )
              )
            )
          )
        )
      )
    )
    .build
}
