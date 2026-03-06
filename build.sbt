ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "tpo",
    libraryDependencies += "org.junit.jupiter" % "junit-jupiter-params" % "6.0.3" % Test
  )
