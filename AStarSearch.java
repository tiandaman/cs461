import java.util.*;

public class AStarSearch {
    // A* search algorithm implementation
    static List<String> aStarSearch(Map<String, List<String>> adjacencyList, Map<String, Main.Coordinates> coordinatesMap, String start, String end) {
        // Priority queue to store nodes with lowest fCost first
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        // Map to store gScore (cost from start to current node)
        Map<String, Double> gScore = new HashMap<>();
        // Map to store fScore (gScore + heuristic) for each node
        Map<String, Double> fScore = new HashMap<>();
        // Map to store parent node for each node in the final path
        Map<String, String> parentMap = new HashMap<>();
        // Set to keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Initialize gScore and fScore for start node
        gScore.put(start, 0.0);
        fScore.put(start, calculateHeuristic(coordinatesMap.get(start), coordinatesMap.get(end)));

        // Add start node to the priority queue
        priorityQueue.add(new Node(start, fScore.get(start)));

        // Main loop for A* search
        while (!priorityQueue.isEmpty()) {
            String currentCity = priorityQueue.poll().city;

            // If current node is the goal, reconstruct and return the path
            if (currentCity.equals(end)) {
                return reconstructPath(parentMap, start, end);
            }

            // Mark current node as visited
            visited.add(currentCity);

            // Explore neighbors of the current node
            for (String neighbor : adjacencyList.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    // Calculate tentative gScore for the neighbor
                    double tentativeGScore = gScore.getOrDefault(currentCity, Double.POSITIVE_INFINITY) + calculateDistance(coordinatesMap.get(currentCity), coordinatesMap.get(neighbor));
                    if (tentativeGScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                        // Update parent node, gScore, and fScore if a better path is found
                        parentMap.put(neighbor, currentCity);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + calculateHeuristic(coordinatesMap.get(neighbor), coordinatesMap.get(end)));
                        // Add the neighbor to the priority queue with its updated fScore
                        priorityQueue.add(new Node(neighbor, fScore.get(neighbor)));
                    }
                }
            }
        }

        return null; // No path found
    }

    // Reconstruct the path from start to end using the parentMap
    static List<String> reconstructPath(Map<String, String> parentMap, String start, String end) {
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

    // Calculate the distance between two coordinates (Euclidean distance)
    static double calculateDistance(Main.Coordinates coord1, Main.Coordinates coord2) {
        double dx = coord1.latitude - coord2.latitude;
        double dy = coord1.longitude - coord2.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Node class to represent a city with its fCost
    static class Node {
        String city;
        double fCost;

        Node(String city, double fCost) {
            this.city = city;
            this.fCost = fCost;
        }
    }
}
