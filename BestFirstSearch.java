import java.util.*;

public class BestFirstSearch {
    // Best-first search algorithm implementation
    static List<String> bestFirstSearch(Map<String, List<String>> adjacencyList, Map<String, Main.Coordinates> coordinatesMap, String start, String end) {
        // Priority queue to store nodes with lowest heuristic first
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.heuristic));
        // Map to store parent node for each node in the final path
        Map<String, String> parentMap = new HashMap<>();
        // Set to keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Add start node to the priority queue with its heuristic value
        priorityQueue.add(new Node(start, calculateHeuristic(coordinatesMap.get(start), coordinatesMap.get(end))));

        // Main loop for best-first search
        while (!priorityQueue.isEmpty()) {
            String currentCity = priorityQueue.poll().city;

            // If current node is the goal, reconstruct and return the path
            if (currentCity.equals(end)) {
                return getPath(parentMap, start, end);
            }

            // Mark current node as visited
            visited.add(currentCity);

            // Explore neighbors of the current node
            for (String neighbor : adjacencyList.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    // Add neighbor to the priority queue with its heuristic value
                    priorityQueue.add(new Node(neighbor, calculateHeuristic(coordinatesMap.get(neighbor), coordinatesMap.get(end))));
                    // Mark neighbor as visited and update parent node
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                }
            }
        }

        return null; // No path found
    }

    // Reconstruct the path from start to end using the parentMap
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

    // Calculate the heuristic (Euclidean distance) between two coordinates
    static double calculateHeuristic(Main.Coordinates cityCoords, Main.Coordinates targetCoords) {
        double dx = cityCoords.latitude - targetCoords.latitude;
        double dy = cityCoords.longitude - targetCoords.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Node class to represent a city with its heuristic value
    static class Node {
        String city;
        double heuristic;

        Node(String city, double heuristic) {
            this.city = city;
            this.heuristic = heuristic;
        }
    }
}
