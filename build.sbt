import com.typesafe.sbt.SbtScalariform.{ScalariformKeys, _}
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt.dsl._
import spray.revolver.RevolverPlugin.Revolver

import scalariform.formatter.preferences.{SpacesAroundMultiImports, Preserve, DanglingCloseParenthesis, PreserveSpaceBeforeArguments, AlignSingleLineCaseStatements, DoubleIndentClassDeclaration}

name := "piper"
organization  := "de.zalando"
version := "0.0.1"

scalaVersion := "2.11.7"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8", "-Xexperimental")

libraryDependencies ++= {
  val scalaTestV       = "3.0.0-M1"
  val scalaMockV       = "3.2.2"
  val akkaStreamV      = "1.0"
  Seq(
    "io.undertow"              % "undertow-core"                          % "1.3.14.Final",
    "com.ning"                 % "async-http-client"                      % "1.9.33",
    "io.netty"                 % "netty-all"                              % "4.0.34.Final",
    "ch.qos.logback"           % "logback-classic"                        % "1.1.3",
    "com.typesafe"             % "config"                                 % "1.3.0",
    "org.scala-lang"           % "scala-reflect"                          % "2.11.7",
    "org.scala-lang.modules"  %% "scala-xml"                              % "1.0.4",
    "com.google.code.findbugs" % "jsr305"                                 % "1.3.9",
    "com.google.guava"         % "guava"                                  % "19.0",
    "org.monifu"                  %% "monifu"                             % "1.0",
    "de.zalando"              %% "beard"                                  % "0.0.6" exclude("org.antlr", "antlr-runtime") exclude("org.antlr", "ST4"),

    // parse route stuff
    "org.scala-lang.modules"  %% "scala-parser-combinators"               % "1.0.4",

    // test stuff
    "org.scalatest"           %% "scalatest"                              % scalaTestV       % "test",
    "org.scalamock"           %% "scalamock-scalatest-support"            % scalaMockV       % "test"
  )
}

lazy val root = project.in(file(".")).configs(IntegrationTest)
Defaults.itSettings
scalariformSettingsWithIt
Revolver.settings
enablePlugins(JavaAppPackaging)

resolvers += Resolver.bintrayRepo("zalando", "maven")

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(PreserveSpaceBeforeArguments, true)
  .setPreference(DanglingCloseParenthesis, Preserve)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(SpacesAroundMultiImports, false)

parallelExecution in IntegrationTest := false

fork in run := true
