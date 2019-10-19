package design.hamu.util.webui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Footer {
  val component = ScalaComponent
    .builder[Unit]("Footer")
    .renderStatic(
      <.footer(
        ^.className := "w-100 position-absolute bg-dark p-4 text-white",
        ^.bottom := "0",
        ^.height := "72"
      )(
        <.div(^.className := "container")(
          <.span(^.className := "text-muted")("Developed by "),
          <.span(^.className := "text-white")("Hamuhouse")
        )
      )
    )
    .build
}
