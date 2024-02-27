import java.util.*;

public class BestFirstSearch {
    static List<String> bestFirstSearch(Map<String, List<String>> adjacencyList, Map<String, Main.Coordinates> coordinatesMap, String start, String end) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.heuristic));
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        priorityQueue.add(new Node(start, calculateHeuristic(coordinatesMap.get(start), coordinatesMap.get(end))));

        while (!priorityQueue.isEmpty()) {
            String currentCity = priorityQueue.poll().city;

            if (currentCity.equals(end)) {
                return getPath(parentMap, start, end);
            }

            visited.add(currentCity);

            for (String neighbor : adjacencyList.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    priorityQueue.add(new Node(neighbor, calculateHeuristic(coordinatesMap.get(neighbor), coordinatesMap.get(end))));
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                }
            }
        }

        return null; // No path found
    }

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

    static double calculateHeuristic(Main.Coordinates cityCoords, Main.Coordinates targetCoords) {
        // Assuming Euclidean distance as heuristic
        double dx = cityCoords.latitude - targetCoords.latitude;
        double dy = cityCoords.longitude - targetCoords.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    static class Node {
        String city;
        double heuristic;

        Node(String city, double heuristic) {
            this.city = city;
            this.heuristic = heuristic;
        }
    }
}
