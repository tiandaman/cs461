import java.util.*;

public class IterativeDeepeningDFSearch {
    static List<String> iterativeDeepeningDFSearch(Map<String, List<String>> adjacencyList, String start, String end) {
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            List<String> path = depthLimitedSearch(adjacencyList, start, end, depth);
            if (path != null) {
                return path;
            }
        }
        return null; // No path found
    }

    static List<String> depthLimitedSearch(Map<String, List<String>> adjacencyList, String current, String end, int depth) {
        return depthLimitedSearch(adjacencyList, current, end, depth, new HashSet<>());
    }

    static List<String> depthLimitedSearch(Map<String, List<String>> adjacencyList, String current, String end, int depth, Set<String> visited) {
        if (current.equals(end)) {
            return Arrays.asList(end);
        }

        if (depth == 0) {
            return null;
        }

        visited.add(current);

        for (String neighbor : adjacencyList.get(current)) {
            if (!visited.contains(neighbor)) {
                List<String> path = depthLimitedSearch(adjacencyList, neighbor, end, depth - 1, visited);
                if (path != null) {
                    path.add(0, current);
                    return path;
                }
            }
        }

        return null;
    }
}
