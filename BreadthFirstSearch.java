import java.util.*;

public class BreadthFirstSearch {
    // Breadth-first search algorithm implementation
    static List<String> breadthFirstSearch(Map<String, List<String>> adjacencyList, String start, String end) {
        // Map to store parent node for each node in the final path
        Map<String, String> parentMap = new HashMap<>();
        // Queue to store nodes to be visited
        Queue<String> queue = new LinkedList<>();
        // Set to keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Add start node to the queue and mark it as visited
        queue.add(start);
        visited.add(start);

        // Main loop for breadth-first search
        while (!queue.isEmpty()) {
            String current = queue.poll(); // Get the next node from the queue

            // If current node is the goal, reconstruct and return the path
            if (current.equals(end)) {
                return getPath(parentMap, start, end);
            }

            // Explore neighbors of the current node
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) { // Check if neighbor has not been visited
                    queue.add(neighbor); // Add neighbor to the queue
                    visited.add(neighbor); // Mark neighbor as visited
                    parentMap.put(neighbor, current); // Update parent node
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
            current = parentMap.get(current); // Move to the parent node
        }
        path.add(start);
        Collections.reverse(path); // Reverse the path to get it from start to end
        return path;
    }
}
