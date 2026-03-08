ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "tpo",

    libraryDependencies ++= Seq(
        "org.junit.jupiter" % "junit-jupiter-api" % "6.0.3" % Test,
        "org.junit.jupiter" % "junit-jupiter-engine" % "6.0.3" % Test,
        "com.github.sbt.junit" % "jupiter-interface" % "0.17.0" % Test
    )
  )

Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
Test / fork := true