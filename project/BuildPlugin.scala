import sbt._
import Keys._

/**
 *
 */
object BuildPlugin extends Build {

  /* Project definition */

  /* Main project, compiling using the compiler dependencies */
  lazy val root = Project(
    id = "root",
    base = file(".")) settings (
      /* TODO : will see later if we need other deps. */
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _),
      publishArtifact in Compile := false) settings (globalSettings: _*)

  /* Project settings */

  /* General settings of the plugin */
  lazy val globalSettings = Seq(
    name := "ASTPersistencePlugin")

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