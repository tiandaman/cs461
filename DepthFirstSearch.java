import java.util.*;

public class DepthFirstSearch {
    // Depth-first search algorithm implementation
    static List<String> depthFirstSearch(Map<String, List<String>> adjacencyList, String start, String end) {
        // Map to store parent node for each node in the final path
        Map<String, String> parentMap = new HashMap<>();
        // Stack to store nodes to be visited
        Stack<String> stack = new Stack<>();
        // Set to keep track of visited nodes
        Set<String> visited = new HashSet<>();

        // Add start node to the stack and mark it as visited
        stack.push(start);
        visited.add(start);

        // Main loop for depth-first search
        while (!stack.isEmpty()) {
            String current = stack.pop(); // Get the next node from the stack

            // If current node is the goal, reconstruct and return the path
            if (current.equals(end)) {
                return BreadthFirstSearch.getPath(parentMap, start, end); // Utilizing BreadthFirstSearch's getPath method
            }

            // Explore neighbors of the current node
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) { // Check if neighbor has not been visited
                    stack.push(neighbor); // Add neighbor to the stack
                    visited.add(neighbor); // Mark neighbor as visited
                    parentMap.put(neighbor, current); // Update parent node
                }
            }
        }

        return null; // No path found
    }
}
