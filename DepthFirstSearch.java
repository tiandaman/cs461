import java.util.*;

public class DepthFirstSearch {
    static List<String> depthFirstSearch(Map<String, List<String>> adjacencyList, String start, String end) {
        Map<String, String> parentMap = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (current.equals(end)) {
                return BreadthFirstSearch.getPath(parentMap, start, end);
            }
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return null;
    }
}
