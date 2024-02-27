import java.util.*;

public class AStarSearch {
    static List<String> aStarSearch(Map<String, List<String>> adjacencyList, Map<String, Main.Coordinates> coordinatesMap, String start, String end) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        gScore.put(start, 0.0);
        fScore.put(start, calculateHeuristic(coordinatesMap.get(start), coordinatesMap.get(end)));

        priorityQueue.add(new Node(start, fScore.get(start)));

        while (!priorityQueue.isEmpty()) {
            String currentCity = priorityQueue.poll().city;

            if (currentCity.equals(end)) {
                return reconstructPath(parentMap, start, end);
            }

            visited.add(currentCity);

            for (String neighbor : adjacencyList.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    double tentativeGScore = gScore.getOrDefault(currentCity, Double.POSITIVE_INFINITY) + calculateDistance(coordinatesMap.get(currentCity), coordinatesMap.get(neighbor));
                    if (tentativeGScore < gScore.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                        parentMap.put(neighbor, currentCity);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + calculateHeuristic(coordinatesMap.get(neighbor), coordinatesMap.get(end)));
                        priorityQueue.add(new Node(neighbor, fScore.get(neighbor)));
                    }
                }
            }
        }

        return null; // No path found
    }

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

    static double calculateHeuristic(Main.Coordinates cityCoords, Main.Coordinates targetCoords) {
        // Assuming Euclidean distance as heuristic
        double dx = cityCoords.latitude - targetCoords.latitude;
        double dy = cityCoords.longitude - targetCoords.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    static double calculateDistance(Main.Coordinates coord1, Main.Coordinates coord2) {
        double dx = coord1.latitude - coord2.latitude;
        double dy = coord1.longitude - coord2.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }

    static class Node {
        String city;
        double fCost;

        Node(String city, double fCost) {
            this.city = city;
            this.fCost = fCost;
        }
    }
}
