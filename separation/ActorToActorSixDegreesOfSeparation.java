
package wpialgs.hw04.separation;

import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Movie;
import wpialgs.sixdegrees.utils.Actor;




/**
 * Creates an Actor-Actor graph where:
 * <ul>
 * <li>Vertices: Actor names
 * <li>Edges: two Actors are connected iff they appeared in the same movie
 * </ul>
 */
public class ActorToActorSixDegreesOfSeparation extends AbstractDegreeOfSeparation {

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using "Kevin Bacon" as the source
     * actor.
     */
    public ActorToActorSixDegreesOfSeparation() {
        super();
    }

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using the specified source actor.
     *
     * @param sourceActor
     *            Actor that will be serving as the source of our search.
     */
    public ActorToActorSixDegreesOfSeparation(String sourceActor) {
        super(sourceActor);
    }

    /**
     * Using the actors and movies maps, create an Actor graph where:
     * <ul>
     * <li>Vertices: Actor names
     * <li>Edges: two Actors are connected iff they appeared in the same movie
     * </ul>
     */
    @Override
    public void createGraph() {
        // Iterate through each movie
        for(String actor : myActors.keys()) {
            myG.addVertex(actor);
        }
        for (String movieName : myMovies.keys()) {
            // Retrieve the Movie object corresponding to the movieName
            Movie movie = myMovies.get(movieName);
            // Iterate through the actors who appeared in this movie
            for (Actor actor1 : movie.getActors()) {
                for (Actor actor2 : movie.getActors()) {
                    // Add an edge between actor1 and actor2 if they are different actors
                    if (!actor1.equals(actor2)) {
                        myG.addEdge(actor1.name, actor2.name);
                    }
                }
            }
        }
    }



    /**
     * Creates a frequency chart containing statistics about the source's distance number.
     */
    @Override
    public void createFrequencyChart() {
        for (Vertex v : myG.getVertices()) {
            myHistogram.record(v.distance);
        }
    }

    /**
     * Computes the Hollywood Number for the source.
     *
     * @return Average distance number of all the actors.
     */
    @Override
    public double computeHollywoodNumber() {
        int count = 0;
        double sum = 0;



        // Iterate over the vertices to calculate the sum of distances
        for (Vertex v : myG.getVertices()) {
            // Check if the vertex is reachable from the source and not the source itself
            if (v.distance != Vertex.INFINITY) {
                sum += v.distance;
                count++;
            }
        }

        if (count == 0) {
            return 0; // Hollywood number is 0 if there are no reachable actors
        }


        // Calculate the average distance (Hollywood number)
        return sum / count;
    }



    /**
     * Create a string with the chain from source to specified actor or actress. If no such actor or actress, it will
     * generate the appropriate error message as a string.
     *
     * @param name
     *            for actor or actress.
     *
     * @return A string representation of the chain
     */
    @Override
    public String chainAsString(String name) {
        Vertex start = myG.getVertex(mySource);
        Vertex dest = myG.getVertex(name);

        // Check if source exists
        StringBuilder sb = new StringBuilder();
        if (start == null) {
            sb.append("Source actor/actress: ").append(mySource).append(" does not exist in our graph.\n");
        } else {
            if (dest == null) {
                sb.append("Destination actor/actress: ").append(name).append(" does not exist in our graph.\n");
            } else {
                sb.append(start.name).append(" and ").append(dest.name).append(" have a distance of ");
                if (dest.distance == Vertex.INFINITY) {
                    sb.append("infinity.\n");
                } else {
                    sb.append(dest.distance).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in a movie with ")
                                .append(dest.predecessor).append(".\n");
                        dest = dest.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}