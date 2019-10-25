import sbt._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

case class Module(org: String, name: String, version: String) {
  def jvm: Def.Initialize[ModuleID] = Def.setting(org %% name % version)
  def js: Def.Initialize[ModuleID] = Def.setting(org %%% name % version)
}

object Dependencies {
  object Scala {
    val v2 = "2.12.10"
    val v3 = "2.13.1"
  }
  object Cats {
    private val version = "2.0.0"
    val core = Module("org.typelevel", "cats-core", version)
    val effect =  Module("org.typelevel", "cats-effect", version)
    val testEffect = Module("com.codecommit", "cats-effect-testing-scalatest", "0.3.0")
  }
  object Mongo {
    private val version = "2.7.0"
    val driver = Module("org.mongodb.scala", "mongo-scala-driver", version)
  }
  object FS2 {
    private val version = "2.0.1"
    val core = Module("co.fs2", "fs2-core", version)
    val reactiveStreams = Module("co.fs2", "fs2-reactive-streams", version)
  }
  object ScalaMock {
    private val version = "4.4.0"
    val core = Module("org.scalamock", "scalamock", version)
  }
  object ScalaTest {
    private val version = "3.0.8"
    val core = Module("org.scalactic", "scalactic", version)
  }
  object ScalaJsReact {
    private val version = "1.4.2"
    val core = Module("com.github.japgolly.scalajs-react", "core", version)
    val extra = Module("com.github.japgolly.scalajs-react", "extra", version)
  }
}
