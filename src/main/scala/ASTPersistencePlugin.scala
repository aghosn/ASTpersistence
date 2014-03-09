
import scala.tools.nsc.{ Global, Phase }
import scala.tools.nsc.plugins.{ Plugin, PluginComponent }

/**
 * @brief main class of the plugin.
 */
class ASTPersistencePlugin(val global: Global) extends Plugin {
  import global._

  val name = "AST persistence plugin"
  val description = """Saves the AST into the .class. 
	For more information https://github.com/aghosn/ASTpersistence""""
  val components = List[PluginComponent](Component) // Might change name

  private object Component extends PluginComponent {
    import global._

    val global = ASTPersistencePlugin.this.global

    override val runsAfter = List("typer")
    val phaseName = ASTPersistencePlugin.this.name

    def newPhase(prev: Phase) = new ASTPersistencePhase(prev)

    /** Additional phase of the compiler for the ASTPersistence */
    class ASTPersistencePhase(prev: Phase) extends StdPhase(prev) {
      override def name = ASTPersistencePlugin.this.name
      def apply(unit: CompilationUnit) {
        new ASTPersistenceTraverser(unit) traverse unit.body
      }
    }

    /** Traverser for the AST Persistence plugin */
    class ASTPersistenceTraverser(unit: CompilationUnit) extends Traverser {

      override def traverse(tree: Tree): Unit = tree match {
        case _ => println(showRaw(tree, true, true, true, false))
      }
    }

  }
}
