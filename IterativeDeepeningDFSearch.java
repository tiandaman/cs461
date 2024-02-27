import java.util.*;

public class IterativeDeepeningDFSearch {
    // Iterative deepening depth-first search algorithm implementation
    static List<String> iterativeDeepeningDFSearch(Map<String, List<String>> adjacencyList, String start, String end) {
        // Iteratively increases depth until the maximum integer value
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            // Perform depth-limited search with increasing depth
            List<String> path = depthLimitedSearch(adjacencyList, start, end, depth);
            if (path != null) {
                return path; // If a path is found, return it
            }
        }
        return null; // No path found within maximum depth
    }

    // Depth-limited search within a specified depth limit
    static List<String> depthLimitedSearch(Map<String, List<String>> adjacencyList, String current, String end, int depth) {
        return depthLimitedSearch(adjacencyList, current, end, depth, new HashSet<>());
    }

    // Helper function for depth-limited search
    static List<String> depthLimitedSearch(Map<String, List<String>> adjacencyList, String current, String end, int depth, Set<String> visited) {
        // If current node is the goal, return the path containing only the goal node
        if (current.equals(end)) {
            return Arrays.asList(end);
        }

        // If depth limit reached or node has been visited, return null indicating no path found
        if (depth == 0 || visited.contains(current)) {
            return null;
        }

        visited.add(current); // Mark current node as visited

        // Explore neighbors of the current node
        for (String neighbor : adjacencyList.get(current)) {
            if (!visited.contains(neighbor)) {
                // Recursively perform depth-limited search on unvisited neighbors
                List<String> path = depthLimitedSearch(adjacencyList, neighbor, end, depth - 1, visited);
                if (path != null) {
                    path.add(0, current); // Prepend the current node to the found path
                    return path; // Return the found path
                }
            }
        }

        return null; // No path found
    }
}
