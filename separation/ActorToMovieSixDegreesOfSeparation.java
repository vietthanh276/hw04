
package wpialgs.hw04.separation;

import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Movie;
import wpialgs.sixdegrees.utils.Actor;

/**
 * Creates an Actor-Movie graph where:
 * <ul>
 * <li>Vertices: Actor and Movie names
 * <li>Edges: an Actor is connected to a Movie, iff he or she appeared in that movie
 * </ul>
 */
public class ActorToMovieSixDegreesOfSeparation extends AbstractDegreeOfSeparation {

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using "Kevin Bacon" as the source
     * actor.
     */
    public ActorToMovieSixDegreesOfSeparation() {
        super();
    }

    /**
     * This allows us to compute the degrees of separation for actors in Hollywood using the specified source actor.
     *
     * @param sourceActor
     *            Actor that will be serving as the source of our search.
     */
    public ActorToMovieSixDegreesOfSeparation(String sourceActor) {
        super(sourceActor);
    }

    @Override
    public void createGraph() {
        // Add vertices for actors
        for (String actor : myActors.keys()) {
            myG.addVertex(actor);
        }

        // Add vertices for movies
        for (String movieName : myMovies.keys()) {
            myG.addVertex(movieName);
        }

        // Iterate through each movie
        for (String movieName : myMovies.keys()) {
            // Retrieve the Movie object corresponding to the movieName
            Movie movie = myMovies.get(movieName);
            // Add edges between actors and the movie
            for (Actor actor : movie.getActors()) {
                myG.addEdge(actor.name, movieName);
            }
            // Add edges between the movie and all actors who appeared in it
            for (Actor actor1 : movie.getActors()) {
                for (Actor actor2 : movie.getActors()) {
                    if (!actor1.equals(actor2)) {
                        myG.addEdge(movieName, actor2.name);
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
        // Iterate through all vertices in the graph
        for (Vertex v : myG.getVertices()) {
            // Check if the vertex represents an actor-to-movie connection
            if (v.distance == Vertex.INFINITY && !myMovies.contains(v.name)) {
                myHistogram.record(v.distance);
            }
                // Record the distance directly for actor-to-movie connections
                else if (v.distance % 2 == 0) {
                    // Record the distance of the vertex divided by 2
                    // This accounts for the fact that two actors occur in the same movie
                    myHistogram.record(v.distance / 2);
                }
            }
        }




    /**
     * Computes the Hollywood Number for the source.
     *
     * @return Average Bacon number of all the actors.
     */
    @Override
    public double computeHollywoodNumber() {
        int count = 0;
        double sum = 0;

        // Iterate over the vertices to calculate the sum of distances
        for (Vertex v : myG.getVertices()) {
            // Check if the vertex is reachable from the source and the distance is even
            if (v.distance % 2 == 0) {
                // Add the distance to the sum, divided by 2 as two actors occur in the same movie
                sum += v.distance / 2;
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
                    sb.append(dest.distance / 2).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in \"").append(dest.predecessor)
                                .append("\" with ").append(dest.predecessor.predecessor).append(".\n");
                        dest = dest.predecessor.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}