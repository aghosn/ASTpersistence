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
    .configs(PluginTest) .settings ( inConfig(PluginTest)(Defaults.testSettings) : _*)
     /* TODO : will see later if we need other deps. */ 
     .settings (libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _)) 
     .settings (globalSettings: _*)

  lazy val PluginTest = config("PluginTest") extend Test

  /* Project settings */

  /* General settings of the plugin */
  lazy val globalSettings = Seq(
    name := "ASTPersistencePlugin")

  /* Add the plugin to the compiler for compilation tests */
  lazy val testSettings :  Seq[sbt.Def.Setting[sbt.Task[Seq[String]]]] = Seq(
    scalacOptions in Compile <++= (Keys.`package` in (root, Compile)) map { (jar: File) =>
       val addPlugin = "-Xplugin:" + jar.getAbsolutePath
       /* add plugin timestamp to compiler options to trigger recompile of
        * main after editing the plugin. (Otherwise a 'clean' is needed.) */
       val dummy = "-Jdummy=" + jar.lastModified
       Seq(addPlugin, dummy)
    }
  )

  /* TODO : add testSettings for the compilation of the test */
  
}