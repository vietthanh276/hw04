package wpialgs.hw04;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StopwatchCPU;
import java.io.File;
import java.util.Date;
import wpialgs.hw04.separation.ActorToActorSixDegreesOfSeparation;
import wpialgs.hw04.separation.ActorToMovieSixDegreesOfSeparation;
import wpialgs.hw04.separation.MovieToMovieSixDegreesOfSeparation;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.separation.SixDegreesOfSeparation;

/**
 * This compares the {@link ActorToMovieSixDegreesOfSeparation}, {@link ActorToActorSixDegreesOfSeparation} and
 * {@link MovieToMovieSixDegreesOfSeparation} symbol graphs using the various datasets.
 *
 * @author Yu-Shan Sun
 *
 * @version 1.0
 */
public class HWGraphCompare {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("USAGE: java HWSTCompare <filename> <sourceActor> <sourceMovie>");
        } else {
            String filename = args[0];
            // Welcome message
            Date today = new Date();
            StdOut.println("Welcome to CS 2223 - Symbol Graphs Compare!");
            StdOut.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            StdOut.printf("\tRunning symbol graph experiments on file: %s.\n", filename);
            StdOut.println("------------------------------------");

            // Run experiment
            final String sourceActor = args[1];
            final String sourceMovie = args[2];

            // Use the Actor-Movie Degrees of Separation
            SixDegreesOfSeparation symbolGraph1 = new ActorToMovieSixDegreesOfSeparation(sourceActor);
            symbolGraph1.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: ActorToMovie\n\n");
            runExperiment(symbolGraph1, sourceActor);

            // Use the Actor-Actor Degrees of Separation
            SixDegreesOfSeparation symbolGraph2 = new ActorToActorSixDegreesOfSeparation(sourceActor);
            symbolGraph2.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: ActorToActor\n\n");
            runExperiment(symbolGraph2, sourceActor);

            // Use the Movie-Movie Degrees of Separation
            SixDegreesOfSeparation symbolGraph3 = new MovieToMovieSixDegreesOfSeparation(sourceMovie);
            symbolGraph3.readFile(new File(args[0]), "\t");
            StdOut.println("SYMBOL GRAPH IMPLEMENTATION: MovieToMovie\n\n");
            runExperiment(symbolGraph3, sourceMovie);
        }
    }

    /**
     * A helper method to help us run the experiment.
     *
     * @param symbolGraph
     *            A symbol graph implementation.
     */
    private static void runExperiment(SixDegreesOfSeparation symbolGraph, String source) {
        // Build the graph and time it
        StopwatchCPU timer = new StopwatchCPU();
        symbolGraph.createGraph();
        double currElapsed = timer.elapsedTime();
        System.out.printf("Graph created. Time elapsed: %.5f seconds\n", currElapsed);

        // Traverse the graph using BFS
        UndirectedGraph g = symbolGraph.getGraph();
        timer = new StopwatchCPU();
        symbolGraph.traverseBFS(source);
        currElapsed = timer.elapsedTime();
        System.out.printf("Graph with %d vertices & %d edges traversed using BFS.\n", g.numVertices(), g.numEdges());
        System.out.printf("Time elapsed: %.5f seconds\n", currElapsed);
        System.out.println("------------------------------------\n");

        // Build a histogram of the data
        timer = new StopwatchCPU();
        symbolGraph.createFrequencyChart();
        symbolGraph.getHistogram().report();
        currElapsed = timer.elapsedTime();
        System.out.printf("Histogram printed. Time elapsed: %.5f seconds\n\n", currElapsed);
    }
}