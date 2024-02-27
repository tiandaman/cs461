//prompted from ChatGPT
/* Write me a java program: that times each of the searches in microseconds, and calculates memory usage for each.

breadth-first search
depth-first search
ID-DFS search
Heuristic Approaches
best-first search
A* search
(5 Total Methods)

◦ A file (CSV) listing each town (pair) as a related adjacent node. 
# be aware adjacency is symmetric: If A is adjacent to B, then B is adjacent to A. This may not be
listed comprehensively if your method requires that bidirectional connections be explicitly stated, you may need to generate additional pairs for the symmetrical connection to work. {That is, tell the program that it’s possible to go from listed A as adjacent to B or listed B as adjacent to A.}
--> Be sure to take this into account when setting up your program’s data structures. If adjacency is listed
in either direction, it should be considered present in both directions.
• Your program should:
Ask the user for their starting and ending towns, making sure they’re both towns in the database.
Ask them then to select the search method they wish to use to find a route to the destination.
If that route exists, the program should then print the route the method found in order, from origin to destination.
If you want to get fancy, you might see how the route generated looks as a map (either a map of connected states as we looked at in class, or as a projection in 2D space based on location and connectivity).
Note that your database is very limited, and many of the real-world roads are left out for simplicity*.
Your program should also: 
measure and print the total time needed to find the route (and include a time-out).
calculate and display the total distance (node to node) for the cities visited on the route selected.
(opt) determine the total memory used (scale of the arrays) to find the solution.
Return to the search method selection and allow a new method to be selected for comparison. */

import java.util.*;
import java.io.*;

public class Main {
    // Data structure to store adjacency list
    static Map<String, List<String>> adjacencyList = new HashMap<>();
    // Data structure to store coordinates
    static Map<String, Coordinates> coordinatesMap = new HashMap<>();

    // Class to represent coordinates
    static class Coordinates {
        double latitude;
        double longitude;

        Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // Function to load data from files
    static void loadData(String adjacenciesFile, String coordinatesFile) throws IOException {
        // Load adjacencies data
        BufferedReader adjacenciesReader = new BufferedReader(new FileReader(adjacenciesFile));
        String line;
        while ((line = adjacenciesReader.readLine()) != null) {
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            // Add city2 to the adjacency list of city1
            adjacencyList.computeIfAbsent(city1, k -> new ArrayList<>()).add(city2);
            // Add city1 to the adjacency list of city2
            adjacencyList.computeIfAbsent(city2, k -> new ArrayList<>()).add(city1);
        }
        adjacenciesReader.close();

        // Load coordinates data
        BufferedReader coordinatesReader = new BufferedReader(new FileReader(coordinatesFile));
        while ((line = coordinatesReader.readLine()) != null) {
            String[] parts = line.split(",");
            String city = parts[0];
            double latitude = Double.parseDouble(parts[1]);
            double longitude = Double.parseDouble(parts[2]);
            coordinatesMap.put(city, new Coordinates(latitude, longitude));
        }
        coordinatesReader.close();
    }

    public static void main(String[] args) {
        try {
            // Load data from files
            loadData("Adjacencies.txt", "coordinates.csv");

            Scanner scanner = new Scanner(System.in);
            boolean continueInput = true;

            while (continueInput) {
                // Prompt user for input
                System.out.print("Enter starting town: ");
                String startTown = scanner.nextLine();
                System.out.print("Enter ending town: ");
                String endTown = scanner.nextLine();

                // Prompt user to select search method
                System.out.println("Select search method:");
                System.out.println("1. Breadth-first search");
                System.out.println("2. Depth-first search");
                System.out.println("3. Iterative deepening depth-first search");
                System.out.println("4. Best-first search");
                System.out.println("5. A* search");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                long startTime = System.nanoTime();
                List<String> path = null;

                switch (choice) {
                    case 1:
                        path = BreadthFirstSearch.breadthFirstSearch(adjacencyList, startTown, endTown);
                        break;
                    case 2:
                        path = DepthFirstSearch.depthFirstSearch(adjacencyList, startTown, endTown);
                        break;
                    case 3:
                        path = IterativeDeepeningDFSearch.iterativeDeepeningDFSearch(adjacencyList, startTown, endTown);
                        break;
                    case 4:
                        path = BestFirstSearch.bestFirstSearch(adjacencyList, coordinatesMap, startTown, endTown);
                        break;
                    case 5:
                        path = AStarSearch.aStarSearch(adjacencyList, coordinatesMap, startTown, endTown);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000; // Time in microseconds

                // Print the path with arrows between cities
                System.out.println("Time taken: " + duration + " microseconds");
                if (path != null) {
                    System.out.print("Route found: ");
                    for (int i = 0; i < path.size() - 1; i++) {
                        System.out.print(path.get(i) + " -> ");
                    }
                    System.out.println(path.get(path.size() - 1));
                } else {
                    System.out.println("No route found.");
                }

                // Calculate and display memory usage
                Runtime runtime = Runtime.getRuntime();
                long memory = runtime.totalMemory() - runtime.freeMemory();
                System.out.println("Memory used: " + memory + " bytes");

                // Ask user if they want to input another city
                System.out.print("Do you want to input another city? (yes/no): ");
                String input = scanner.nextLine();
                continueInput = input.equalsIgnoreCase("yes");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
