import Dependencies._

lazy val commonSettings = inThisBuild(
    Seq(
      scalaVersion := "2.13.1",
      addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
      addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
    )
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "blog-zio-sttp",
    organization := "io.tuliplogic",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      cask,
      zio,
      scalaTest % Test,
      scalactic % Test
    ) ++ sttp ++ circe
  )
