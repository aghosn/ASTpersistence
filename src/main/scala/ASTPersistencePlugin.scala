
import scala.tools.nsc.{ Global, Phase }
import scala.tools.nsc.plugins.{ Plugin, PluginComponent }
import scala.collection.mutable.Queue

/**
 * @brief main class of the plugin.
 */
class ASTPersistencePlugin(val global: Global) extends Plugin {
  import global._

  /* *************************************************************************/
  /*                                                                         */
  /* Plugin global declarations                                              */
  /*                                                                         */
  /* *************************************************************************/

  val name = "AST persistence plugin"
  val description = """Saves the AST into the .class. 
	For more information https://github.com/aghosn/ASTpersistence"""

  val components = List[PluginComponent](APComponent) // Might change name

  /* *************************************************************************/
  /*                                                                         */
  /* Plugin Component                                                        */
  /*                                                                         */
  /* *************************************************************************/

  private object APComponent extends PluginComponent {
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

      override def traverse(tree: Tree): Unit = ??? /* TODO */

      def printRaw(tree: Tree): Unit = println(showRaw(tree, true, true, true, false))
    }

    /* ***********************************************************************/
    /*                                                                       */
    /* Phases of the AST Persistence plugin                                  */
    /*                                                                       */
    /* ***********************************************************************/

    /**
     * @brief From a given context, retrieves the list of the names, of the
     * symbols, and of the types.
     *
     * Passed to the expander : the ASTs
     * Output of the expander :
     * 	1. The list of names in BFS order
     *  2. The list of symbols in BFS order
     *  3. The list of types in BFS order
     */
    class RelationExpander {
      
      /* TODO */
      
      def generateBFS[T](tree: Tree)(f : Tree => T) = {
        def recBFS(queue: Queue[Tree])(acc : List[T]) : List[T] = queue.get(0) match {
          case Some(node) => {
            var n_acc = acc
            node.children.foreach{n => queue.enqueue(n); n_acc = f(n)::n_acc}
            queue.dequeue()
            recBFS(queue)(n_acc)
          }
          case None => acc
        }
        
        recBFS(Queue[Tree](tree))(Nil)
      }
    }

    /**
     * @brief Compress the symbols from the list returned by the Relation Expander.
     */
    class SymbolCompressor {
      /* TODO */
    }
    /**
     * @brief Compress the Types (And the types hierarchy) returned by the Relation
     * Expander.
     */
    class TypeCompressor {
      /* TODO */
    }

    /**
     * @brief This class implements the tree compression
     *        Algorithm based on LZW.
     */
    class AstCompressor {
      /* TODO */
    }

  }
}
