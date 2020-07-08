inThisBuild(
  Seq(
    version := "0.1",
    scalaVersion := "2.12.11",
    semanticdbEnabled := true,
    semanticdbVersion :=  "4.3.14",
    scalacOptions += "-Yrangepos",
  )
)

lazy val common = (project in file("modules/common"))

lazy val interfaces = (project in file("modules/interfaces"))
  .dependsOn(common)

lazy val implementations = (project in file("modules/implementations"))
  .dependsOn(interfaces)

lazy val standalone = (project in file("modules/standalone"))

lazy val app = (project in file("modules/app"))
  .dependsOn(
    common,
    interfaces,
    implementations,
    standalone
  )
