package wpialgs.hw04.separation;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.separation.SixDegreesOfSeparation;
import edu.princeton.cs.algs4.Queue;

/**
 * Abstract class for computing degrees of separation using breadth-first search.
 *
 * @version 1.0
 */
public abstract class AbstractDegreeOfSeparation extends SixDegreesOfSeparation {

    /**
     * Constructs an AbstractDegreeOfSeparation object with default parameters.
     */
    public AbstractDegreeOfSeparation() {
        super();
    }

    /**
     * Constructs an AbstractDegreeOfSeparation object with the specified source actor.
     *
     * @param sourceActor Actor serving as the source of the search.
     */
    public AbstractDegreeOfSeparation(String sourceActor) {
        super(sourceActor);
    }

    /**
     * Traverse the graph using breadth-first search on {@code g} from {@code source}.
     * If source is not in the {@code UndirectedGraph}, then the traversal will do nothing.
     *
     * @param g      UndirectedGraph to traverse.
     * @param source Vertex from which to begin the traversal.
     */
    public final void traverseBFS(UndirectedGraph g, Vertex source) {
        Queue<Vertex> q = new Queue<>();
        q.enqueue(source);
        source.distance = 0;
        while (!q.isEmpty()) {
            Vertex v = q.dequeue();
            for (Vertex w : g.adjacentTo(v.name)) {
                if (w.distance == Vertex.INFINITY) {
                    w.distance = v.distance + 1;
                    w.predecessor = v;
                    q.enqueue(w);
                }
            }
        }
    }
}




