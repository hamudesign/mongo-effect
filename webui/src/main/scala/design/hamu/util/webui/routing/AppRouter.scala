package design.hamu.util.webui.routing

import design.hamu.util.webui.components.{Footer, Navbar}
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.html_<^._

object AppRouter {
  def layout(
      routerCtl: RouterCtl[AppPage],
      resolution: Resolution[AppPage]
  ): VdomElement = {
    <.div(
      ^.className := "w-100 position-relative",
      ^.minHeight := "100%",
      ^.paddingBottom := "72px"
    )(
      Navbar.component(),
      resolution.render(),
      Footer.component()
    )
  }
  val config = RouterConfigDsl[AppPage].buildConfig { dsl =>
    import dsl._
    (
      emptyRule
        | staticRoute(root, Home) ~> render(Home.component())
        | staticRoute("#guide", Guide) ~> render(Guide.component())
    ).notFound(redirectToPage(Home)(Redirect.Replace)).renderWith(layout)
  }

  val component = Router.componentUnbuilt(BaseUrl.until_#, config).build
}
