package design.hamu.util.webui

import design.hamu.util.webui.routing.AppRouter
import org.scalajs.dom

object Main extends App {
  AppRouter.component().renderIntoDOM(dom.document.getElementById("app"))
}
