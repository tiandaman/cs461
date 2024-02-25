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

    // Function to perform breadth-first search
    static List<String> breadthFirstSearch(String start, String end) {
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                return getPath(parentMap, start, end);
            }
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null; // No path found
    }

    // Function to perform depth-first search
    static List<String> depthFirstSearch(String start, String end) {
        Map<String, String> parentMap = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (current.equals(end)) {
                return getPath(parentMap, start, end);
            }
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null; // No path found
    }

    // Function to perform iterative deepening depth-first search
    static List<String> iterativeDeepeningDFSearch(String start, String end) {
        // Implementation of ID-DFS algorithm
        // Not implemented in this example
        return null;
    }

    // Function to perform best-first search
    static List<String> bestFirstSearch(String start, String end) {
        // Implementation of best-first search algorithm
        // Not implemented in this example
        return null;
    }

    // Function to perform A* search
    static List<String> aStarSearch(String start, String end) {
        // Implementation of A* search algorithm
        // Not implemented in this example
        return null;
    }

    // Function to get path from parent map
    static List<String> getPath(Map<String, String> parentMap, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        while (!current.equals(start)) {
            path.add(current);
            current = parentMap.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        try {
            // Load data from files
            loadData("Adjacencies.txt", "coordinates.csv");

            // Prompt user for input
            Scanner scanner = new Scanner(System.in);
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

            long startTime = System.nanoTime();
            List<String> path = null;

            switch (choice) {
                case 1:
                    path = breadthFirstSearch(startTown, endTown);
                    break;
                case 2:
                    path = depthFirstSearch(startTown, endTown);
                    break;
                case 3:
                    path = iterativeDeepeningDFSearch(startTown, endTown);
                    break;
                case 4:
                    path = bestFirstSearch(startTown, endTown);
                    break;
                case 5:
                    path = aStarSearch(startTown, endTown);
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000; // Time in microseconds

            if (path != null) {
                System.out.println("Route found:");
                for (String city : path) {
                    System.out.println(city);
                }
            } else {
                System.out.println("Route not found.");
            }

            System.out.println("Time taken: " + duration + " microseconds");

            // Calculate and display memory usage
            Runtime runtime = Runtime.getRuntime();
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Memory used: " + memory + " bytes");

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
