
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
  

  private object Component extends PluginComponent{

    val global : ASTPersistencePlugin.this.global.type = ASTPersistencePlugin.this.global
    val runsAfter = ???
    val phaseName = ASTPersistencePlugin.this.name
    def newPhase(_prev : Phase) = new ASTPersisPhase(_prev)
    
    
    class ASTPersisPhase (prev : Phase )extends StdPhase(prev){
	  override def name = ASTPersistencePlugin.this.name
	  
	  /*See what is needed as argument*/
	  def apply = ???
	    
	  
    }
	
	
  }
}
