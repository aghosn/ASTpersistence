import sbt._
import Keys._

/**
 * @brief sbt options for compilation and tests.
 */
object BuildPlugin extends Build {

  /* Project definition */

  /* Main project, compiling using the compiler dependencies */
  lazy val root = Project(
    id = "root",
    base = file("."))
    /* TODO : will see later if we need other deps. */
    .settings(libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _))
    .settings(globalSettings: _*)

  /* project for examples, compiling using the AST Persistence plugin directly */
  lazy val examples = Project(
    id = "examples",
    base = file("examples/"))
    .settings(globalSettings ++ testSettings: _*)

  /* Other project for messy inspiration source and examples not directly related to the plugin */
  lazy val messy = Project(
    id = "messy",
    base = file("messy/"))
    .settings(globalSettings:_*)

  /* Project settings */

  /* General settings of the plugin */
  lazy val globalSettings = Seq(
    name := "ASTPersistencePlugin",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.2" % "test"
)

  /* Add the plugin to the compiler for compilation tests */
  lazy val testSettings = Seq(
    scalacOptions in Compile <++= (Keys.`package` in (root, Compile)) map { (jar: File) =>
      val addPlugin = "-Xplugin:" + jar.getAbsolutePath
      /* add plugin timestamp to compiler options to trigger recompile of
        * main after editing the plugin. (Otherwise a 'clean' is needed.) */
      val dummy = "-Jdummy=" + jar.lastModified
      Seq(addPlugin, dummy)
    })

  /* TODO : add testSettings for the compilation of the test */

}