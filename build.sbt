scalaVersion := "2.12.7"
crossScalaVersions := Seq("2.10.7", "2.12.7")

lazy val akkaParadox = project
  .in(file("."))
  .settings(
    skip in publish := true
  )
  .aggregate(akkaTheme, akkaPlugin)

lazy val akkaTheme = project
  .in(file("theme"))
  .enablePlugins(ParadoxThemePlugin)
  .settings(
    organization := "com.lightbend.akka",
    name := "paradox-theme-akka",
    libraryDependencies ++= Seq(
      "org.webjars" % "foundation" % "6.3.1" % "provided",
      "org.webjars" % "prettify" % "4-Mar-2013-1" % "provided"
    )
  )

lazy val akkaPlugin = project
  .in(file("plugin"))
  .settings(
    sbtPlugin := true,
    organization := "com.lightbend.akka",
    name := "sbt-paradox-akka",
    bintrayRepository := "sbt-plugin-releases",
    addSbtPlugin(
      "com.lightbend.paradox" % "sbt-paradox" % "0.4.4"
    ),
    resourceGenerators in Compile += Def.task {
      val file = (resourceManaged in Compile).value / "akka-paradox.properties"
      IO.write(file, s"akka.paradox.version=${version.value}")
      Seq(file)
    }
  )
