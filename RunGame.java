
package wpialgs.hw04;

import edu.princeton.cs.algs4.StopwatchCPU;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
import wpialgs.hw04.separation.ActorToMovieSixDegreesOfSeparation;
import wpialgs.sixdegrees.graphs.UndirectedGraph;
import wpialgs.sixdegrees.separation.SixDegreesOfSeparation;

/**
 * Runs the Oracle of Bacon game using the {@link ActorToMovieSixDegreesOfSeparation} symbol graph.
 */
public class RunGame {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USAGE: java RunGame <filename>");
        } else {
            // Welcome message
            Date today = new Date();
            System.out.println("Welcome to CS 2223 - Oracle of Bacon!");
            System.out.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            System.out.printf("\tInput File: %s\n", args[0]);
            System.out.println("------------------------------------");

            // Run the game until we stop
            Scanner sc = new Scanner(System.in);
            String stop = "n";
            do {
                // Prompt for the source actor
                System.out.print("Enter the source actor: ");
                String source = sc.nextLine().replaceAll("\n", "");
                System.out.println();

                // Use the Actor-Movie Degrees of Separation
                SixDegreesOfSeparation symbolGraph = new ActorToMovieSixDegreesOfSeparation(source);
                symbolGraph.readFile(new File(args[0]), "\t");
                symbolGraph.createGraph();
                UndirectedGraph g = symbolGraph.getGraph();

                StopwatchCPU timer = new StopwatchCPU();
                symbolGraph.traverseBFS(source);
                double currElapsed = timer.elapsedTime();
                System.out.printf("Graph with %d vertices & %d edges traversed using BFS.\n", g.numVertices(),
                        g.numEdges());
                System.out.printf("Time elapsed: %.5f seconds\n", currElapsed);
                System.out.println("------------------------------------\n");

                // Print the histogram
                symbolGraph.createFrequencyChart();
                symbolGraph.getHistogram().report();
                System.out.println();

                // Print the Hollywood number
                System.out.printf("Hollywood number: %.5f\n\n", symbolGraph.computeHollywoodNumber());

                // Prompt for new destination actor until we quit
                String dest;
                do {
                    System.out.print("Enter the destination actor or type quit to finish: ");
                    dest = sc.nextLine().replaceAll("\n", "");
                    System.out.println();

                    if (!dest.equalsIgnoreCase("quit")) {
                        System.out.println(symbolGraph.chainAsString(dest));
                    }
                } while (!dest.equalsIgnoreCase("quit"));

                // Check to see if we want to keep playing
                System.out.print("Continue playing (y/n)?: ");
                stop = sc.nextLine().replaceAll("\n", "");
                System.out.println("\n------------------------------------");
            } while (!stop.equalsIgnoreCase("n"));
        }
    }
}