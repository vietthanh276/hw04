
package wpialgs.hw04.utils;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinarySearchST;

/**
 * <p>
 * This class requires you complete the {@link #record(int)}, {@link #isEmpty()}, {@link #minimum()}, {@link #maximum()}
 * and {@link #report()} methods by using a symbol table. The {@link #report(int)} method is complete and doesn't
 * require any change.
 * </p>
 *
 * @version 3.0
 */
public class Histogram {

    // Class attributes
    public BinarySearchST<Integer, Integer> st;
    private final String myTitle;
    private final String myCol1Label;
    private final String myCol2Label;

    // You will need some symbol table that you can use to store the keys
    // in such a way that you can retrieve them in order.
    // NOTE: The book suggests a couple of implementations that would work!

    /**
     * This creates an object for storing data that can later be displayed as a histogram.
     *
     * @param title
     *            Title for this histogram
     * @param col1Label
     *            Label for column 1
     * @param col2Label
     *            Label for column 2
     */
    public Histogram(String title, String col1Label, String col2Label) {
        myTitle = title;
        myCol1Label = col1Label;
        myCol2Label = col2Label;

        st = new BinarySearchST<>();
    }

    /**
     * Increase the count for the number of times {@code key} exists in the {@link Histogram}. Note that if the user
     * passes in {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE}, we treat those as {@code -infinity} and
     * {@code infinity}.
     *
     * @param key
     *            A key value in the histogram.
     */
    public void record(int key) {
        if (st.contains(key)) {
            st.put(key, st.get(key) + 1);
        } else {
            st.put(key,1);
        }
    }

    /**
     * Return whether histogram is empty.
     *
     * @return {@code true} if the histogram is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return st.isEmpty();
    }

    /**
     * Return the lowest integer key in the histogram.
     *
     * @return The minimum value key in the histogram
     */
    public int minimum() {
        return st.min();
    }

    /**
     * Return the largest integer key in the histogram.
     *
     * @return The maximum value key in the histogram
     */
    public int maximum() {
        return st.max();
    }

    /**
     * Return sum of counts for keys from lo (inclusive) to high (inclusive).
     *
     * @param lo
     *            Lowest key value
     * @param hi
     *            Highest key value
     *
     * @return Sum of counts
     */
    public int total(int lo, int hi) {
        int totalCount = 0;
        for (int key : st.keys(lo, hi)) {
            totalCount += st.get(key);
        }
        return totalCount;
    }

    /**
     * Produce a report for all keys (and their counts) in ascending order of keys.
     */
    public void report() {
        // Histogram header
        StdOut.println(myTitle);
        StdOut.printf("%-15s\t\t%-15s\n", myCol1Label, myCol2Label);
        StdOut.println("---------------\t\t---------------");

        // Iterate over the entries in the symbol table and print each key-value pair
        for (int key : st.keys()) {
            int currCol1 = key;
            int currCol2 = st.get(key);
            StdOut.printf("%-15d\t\t%15d\n", currCol1, currCol2);
        }
    }

    /**
     * Produce a report for all bins (with aggregate counts) in ascending order by range.
     * <p>
     * The first range label that is output should be "0 - (binSize-1)" since the report always starts from 0.
     * <p>
     * It is acceptable if the final range label includes values that exceed maximum().
     *
     * @param binSize
     *            The specified bin size for grouping keys
     */
    public void report(int binSize) {
        // USE AS-IS. NO CHANGE REQUIRED!
        // Histogram header
        StdOut.println(myTitle + " (binSize=" + binSize + ")");
        StdOut.printf("%-15s\t\t%-15s\n", myCol1Label, myCol2Label);
        StdOut.println("---------------\t\t---------------");

        // Prints the contents of the histogram
        for (int i = minimum(); i <= maximum(); i += binSize) {
            StdOut.printf("%-15s\t\t%15d\n", String.format("%d-%d", i, i + binSize - 1), total(i, i + binSize - 1));
        }
    }
}