# Oracle of Bacon Game

This package contains a Java program that implements the Oracle of Bacon game using the Six Degrees of Separation algorithm.

## Description

The `RunGame` class serves as the main entry point to the program. It prompts the user to enter a source actor, then uses the Six Degrees of Separation algorithm to find connections between actors based on shared movies. 
The program reads a file containing actor-movie data and constructs a graph representing the connections between actors and movies. It then performs a breadth-first search (BFS) traversal of the graph to find the shortest paths 
between the source actor and other actors in the network. Finally, it displays the results, including the Hollywood number (the average distance between actors) and the shortest path from the source actor to a destination actor.

## Usage

To run the program, provide the filename containing the actor-movie data as a command-line argument. For example:
java RunGame <filename>
