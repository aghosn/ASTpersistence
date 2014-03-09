/**
 * @brief From a given context, retrieves the list of the names, of the 
 * symbols, and of the types.
 */
import scala.tools.nsc.{ Global, Phase }
import scala.collection.mutable.Queue
import scala.reflect.api.Universe


class RelationExpander(val global : Global){
  import global._
  
  def generateBFS(tree : Tree) = {
	def recBFS(tree : Tree , queue : Queue[Tree]) = queue.get(0) match {
	  case Some(node) => {
	    node.children.foreach(n => queue.enqueue(n))
	   queue.dequeue()
	  }
	  case None => println("End of queue");
	}
	recBFS(tree, new Queue[Tree]())
  } 
    

}
