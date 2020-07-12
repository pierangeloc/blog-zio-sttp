import sbt._

object Dependencies {


  val http4sVersion = "0.21.0-M6"

  val zioVersion       = "1.0.0-RC18"
  val zioCatsVersion   = "2.1.3.0-RC16"
  val zioNioVersion    = "0.4.0"
  val zioMacrosVersion = "0.6.0"

  val fs2Version        = "2.1.0"
  val catsVersion       = "2.1.0"
  val catsEffectVersion = "2.0.0"
  val circeVersion      = "0.12.1"

  lazy val scalaTest  = "org.scalatest" %% "scalatest"   % "3.2.0"
  lazy val scalactic  = "org.scalactic" %% "scalactic" % "3.2.0"

  lazy val cats       = "org.typelevel" %% "cats-core"   % catsVersion
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
  lazy val catsLaws   = "org.typelevel" %% "cats-laws"   % "1.1.0"

  lazy val zio         = "dev.zio"       %% "zio"              % zioVersion
  lazy val zioStreams  = "dev.zio"       %% "zio-streams"      % zioVersion
  lazy val zioCats     = "dev.zio"       %% "zio-interop-cats" % zioCatsVersion

  lazy val cask        =  "com.lihaoyi" %% "cask" % "0.6.5"

  lazy val fs2         = "co.fs2" %% "fs2-io" % fs2Version

  val sttp = {
    def module(name: String, version: String = "2.2.1") =
      "com.softwaremill.sttp.client" %% name % version

    List(
      module("core"),
      module("async-http-client-backend-zio"),
      module("circe")
    )
  }

  val circe = {
    def module(name: String, version: String = "0.12.1") =
      "io.circe" %% s"circe-$name"           % version

    List(
      module("core"),
      module("generic"),
      module("generic-extras"),
      module("parser"),
      module("refined")
    )
  }
}