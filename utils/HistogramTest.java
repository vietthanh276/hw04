
package wpialgs.hw04.utils;

import java.util.Random;

/**
 * This is a histogram test class that can be used to test your implementation.
 *
 * @version 2.0
 */
public class HistogramTest {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("USAGE: java HistogramTest <random_number_seed> <total_random_numbers>");
        } else {
            Histogram sample = new Histogram("Histogram", "Keys", "Frequency");

            // Use a random number generator seed, so each run you get same pseudorandom numbers.
            int seed = Integer.parseInt(args[0]);
            int totalRandomNums = Integer.parseInt(args[1]);
            Random rnd = new Random(seed);
            for (int i = 0; i < totalRandomNums; i++) {
                int v = rnd.nextInt(totalRandomNums);
                sample.record(v);
            }

            sample.report();
            sample.report(1);
            sample.report(5);
        }
    }
}