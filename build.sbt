ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """todo-app""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play"    % "5.1.0" % Test,
      "com.typesafe.play"      %% "play-slick"            % "5.0.2",
      "com.typesafe.play"      %% "play-slick-evolutions" % "5.0.2",
      "mysql" % "mysql-connector-java" % "8.0.29",
    )
  )
