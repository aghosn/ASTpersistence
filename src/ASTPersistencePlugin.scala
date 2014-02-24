
import scala.tools.nsc.{ Global, Phase }
import scala.tools.nsc.plugins.{ Plugin, PluginComponent }

/**
 * @brief main class of the plugin.
 */ 
class ASTPersistencePlugin(global: Global) extends Plugin {

  val name = "AST persistence plugin"
  val description = """Saves the AST into the .class. 
	For more information https://github.com/aghosn/ASTpersistence""""
  val components = List[PluginComponent](Component) // Might change name
  

  /* TODO : the plugin component, the new phase, and the traverser */
  private object Component extends PluginComponent{
	
  }
}
