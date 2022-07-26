ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """todo-app""",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "mysql" % "mysql-connector-java" % "8.0.29"
    )
  )