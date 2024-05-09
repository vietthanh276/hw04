
package wpialgs.hw04.separation;



import wpialgs.sixdegrees.graphs.Vertex;
import wpialgs.sixdegrees.utils.Movie;
import wpialgs.sixdegrees.utils.Actor;

/**
 * Creates a Movie-Movie graph where:
 * <ul>
 * <li>Vertices: Movie names
 * <li>Edges: two Movies are connected iff they share an Actor
 * </ul>
 */
public class MovieToMovieSixDegreesOfSeparation extends AbstractDegreeOfSeparation {

    /**
     * This allows us to compute the degrees of separation for movies.
     */
    public MovieToMovieSixDegreesOfSeparation() {
        this("X-Men: First Class");
    }

    /**
     * This allows us to compute the degrees of separation for movies in Hollywood using the specified source movie.
     *
     * @param sourceMovie
     *            Movie that will be serving as the source of our search.
     */
    public MovieToMovieSixDegreesOfSeparation(String sourceMovie) {
        super(sourceMovie);
    }

    /**
     * Using the actors and movies maps, create a Movie graph where:
     * <ul>
     * <li>Vertices: Movie names
     * <li>Edges: two Movies are connected iff they share an Actor
     * </ul>
     */
    @Override
    public void createGraph() {

        // Add vertices for movies
        for (String movieName : myMovies.keys()) {
            myG.addVertex(movieName);
        }
        
        // Iterate through each actor
        for (String actorName : myActors.keys()) {
            // Retrieve the Actor object corresponding to the actorName
            Actor actor = myActors.get(actorName);
            // Iterate through the movies that this actor appeared in
            for (Movie movie1 : actor.getMovies()) {
                for (Movie movie2 : actor.getMovies()) {
                    // Add an edge between movie1 and movie2 if they are different movies
                    if (!movie1.equals(movie2)) {
                        myG.addEdge(movie1.name, movie2.name);
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
            myHistogram.record(v.distance); // Divide by 2 as two movies share the same actor
        }
    }

    /**
     * Computes the Hollywood Number for the source.
     *
     * @return Average distance number of all the movies.
     */
    @Override
    public double computeHollywoodNumber() {
        int count = 0;
        double sum = 0;

        // Check if the graph has no vertices (i.e., is empty)
        if (myG.numVertices() == 0) {
            return 0;
        }

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
     * Create a string with the chain from source to specified movie. If no such movie, it will generate the appropriate
     * error message as a string.
     *
     * @param name
     *            for movie.
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
            sb.append("Source movie: ").append(mySource).append(" does not exist in our graph.\n");
        } else {
            if (dest == null) {
                sb.append("Destination movie: ").append(name).append(" does not exist in our graph.\n");
            } else {
                sb.append(start.name).append(" and ").append(dest.name).append(" have a distance of ");
                if (dest.distance == Vertex.INFINITY) {
                    sb.append("infinity.\n");
                } else {
                    sb.append(dest.distance / 2).append(".\n\n");

                    int counter = 1;
                    while (dest != start) {
                        sb.append(counter).append(". ").append(dest.name).append(" was in \"").append(dest.predecessor)
                                .append("\" with ").append(dest.predecessor).append(".\n");
                        dest = dest.predecessor;
                        counter++;
                    }
                }
            }
        }

        return sb.toString();
    }
}
