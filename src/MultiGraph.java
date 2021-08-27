import java.util.*;

/**
 * Implementation of GraphADT
 */
public class MultiGraph implements GraphADT {

    private Map<Integer, NodeADT> nodes;
    private Map<NodeADT, List<EdgeADT>> edges;

    /**
     * Constructor, requires no arguments performs setup
     */
    public MultiGraph() {
        nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Adds a node to the graph
     *
     * @param node The node to be added to the graph
     */
    @Override
    public void addNode(NodeADT node) {
        nodes.put(node.getId(), node);
    }

    /**
     * Getter for a node from an id
     *
     * @param id The id of the node to find
     * @return The NodeADT object of the node id provided
     */
    @Override
    public NodeADT getNode(int id) {
        return nodes.get(id);
    }

    /**
     * Adds an edge to the graph
     *
     * @param edge The edge to be added to the graph
     */
    @Override
    public void addEdge(EdgeADT edge) {
        List<EdgeADT> edgeList;

        if (edges.containsKey(edge.getNodeA())) {

            edgeList = edges.get(edge.getNodeA());

            for (EdgeADT e : edgeList) {
                if ((e.getNodeA().equals(edge.getNodeA()) && e.getNodeB().equals(edge.getNodeB())) || (e.getNodeA().equals(edge.getNodeB()) && e.getNodeB().equals(edge.getNodeA())) && e.getWeight() == edge.getWeight())
                    return;
            }

            edgeList.add(edge);

        } else {
            edgeList = new ArrayList<>();
            edgeList.add(edge);
            edges.put(edge.getNodeA(), edgeList);
        }

        if (edges.containsKey(edge.getNodeB())) {
            edgeList = edges.get(edge.getNodeB());
            edgeList.add(edge);
        } else {
            edgeList = new ArrayList<>();
            edgeList.add(edge);
            edges.put(edge.getNodeB(), edgeList);
        }
    }

    /**
     * Checks if two nodes are directly connected by one edge
     *
     * @param nodeA One node to check for a connection
     * @param nodeB The other node to check for a connection
     * @return boolean value if two nodes are connected by an edge
     */
    @Override
    public boolean isConnected(NodeADT nodeA, NodeADT nodeB) {
        for (EdgeADT edge : edges.get(nodeA)) {
            if ((nodeA.equals(edge.getNodeA()) && nodeB.equals(edge.getNodeB())) || (nodeA.equals(edge.getNodeB()) && nodeB.equals(edge.getNodeA()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the related edges to a given node
     *
     * @param node The node to find related edges
     * @return List of edges connected to the given node
     */
    @Override
    public List<EdgeADT> getConnected(NodeADT node) {
        return edges.get(node);
    }

    /**
     * Find a path between two given nodes
     *
     * @param nodeA The users current location
     * @param nodeB The users desired location
     * @return A list of edges in the order of the path to be taken
     */
    @Override
    public List<EdgeADT> findPath(NodeADT nodeA, NodeADT nodeB) {

        Comparator<List<EdgeADT>> pathComparator = (path1, path2) -> {

            int length1 = 0;
            int length2 = 0;

            for (EdgeADT edge : path1) {
                length1 += edge.getWeight();
            }
            for (EdgeADT edge : path2) {
                length2 += edge.getWeight();
            }

            return length1 - length2;
        };

        if (nodeA.equals(nodeB))
            return null;

        Queue<List<EdgeADT>> agenda = new PriorityQueue<>(pathComparator);
        Map<NodeADT, Integer> visited = new HashMap<>();
        List<EdgeADT> currentPath = new ArrayList<>();
        List<EdgeADT> newPath;
        List<EdgeADT> nextEdges;
        NodeADT currentNode;
        EdgeADT currentEdge;
        EdgeADT previousEdge;
        int pathLength;

        nextEdges = edges.get(nodeA);
        for (EdgeADT edge : nextEdges) {
            currentPath = new ArrayList<>();
            currentPath.add(edge);
            agenda.add(currentPath);
        }
        visited.put(nodeA, 0);

        while (!agenda.isEmpty()) {

            currentPath = agenda.remove();
            currentEdge = currentPath.get(currentPath.size() - 1);

            if (currentPath.size() > 1) {

                previousEdge = currentPath.get(currentPath.size() - 2);

                if (currentEdge.getNodeA().equals(previousEdge.getNodeA()) || currentEdge.getNodeA().equals(previousEdge.getNodeB()))
                    currentNode = currentEdge.getNodeB();
                else
                    currentNode = currentEdge.getNodeA();
            } else {

                if (nodeA.equals(currentEdge.getNodeA()))
                    currentNode = currentEdge.getNodeB();
                else
                    currentNode = currentEdge.getNodeA();
            }

            pathLength = 0;
            for (EdgeADT edge : currentPath) {
                pathLength += edge.getWeight();
            }

            if (visited.containsKey(currentNode) && visited.get(currentNode) < pathLength)
                continue;
            else
                visited.put(currentNode, pathLength);

            if (currentNode.equals(nodeB))
                break;

            nextEdges = edges.get(currentNode);

            for (EdgeADT nextEdge : nextEdges) {

                newPath = new ArrayList<>(currentPath);

                if (!nextEdge.equals(currentEdge)) {
                    newPath.add(nextEdge);
                    agenda.add(newPath);
                }
            }
        }
        System.out.println("CURRENT PATH: " + currentPath);

        return currentPath;
    }

    /**
     * Find all possible paths between two given nodes
     *
     * @param nodeA The users current location
     * @param nodeB The users desired location
     * @return A list of list of edges. i.e. a list of paths
     */
    @Override
    public List<List<EdgeADT>> findAllPaths(NodeADT nodeA, NodeADT nodeB) {

        Comparator<List<EdgeADT>> pathComparator = (path1, path2) -> {

            int length1 = 0;
            int length2 = 0;

            for (EdgeADT edge : path1) {
                length1 += edge.getWeight();
            }
            for (EdgeADT edge : path2) {
                length2 += edge.getWeight();
            }

            return length1 - length2;
        };

        if (nodeA.equals(nodeB))
            return null;

        List<List<EdgeADT>> paths = new ArrayList<>();
        Queue<List<EdgeADT>> agenda = new PriorityQueue<>(pathComparator);
        Map<NodeADT, Integer> visited = new HashMap<>();
        List<EdgeADT> currentPath;
        List<EdgeADT> newPath;
        List<EdgeADT> nextEdges;
        NodeADT currentNode;
        EdgeADT currentEdge;
        EdgeADT previousEdge;

        nextEdges = edges.get(nodeA);
        for (EdgeADT edge : nextEdges) {
            currentPath = new ArrayList<>();
            currentPath.add(edge);
            agenda.add(currentPath);
        }
        visited.put(nodeA, 0);

        while (!agenda.isEmpty()) {

            currentPath = agenda.remove();
            currentEdge = currentPath.get(currentPath.size() - 1);

            if (currentPath.size() > 1) {

                previousEdge = currentPath.get(currentPath.size() - 2);

                if (currentEdge.getNodeA().equals(previousEdge.getNodeA()) || currentEdge.getNodeA().equals(previousEdge.getNodeB()))
                    currentNode = currentEdge.getNodeB();
                else
                    currentNode = currentEdge.getNodeA();
            } else {

                if (nodeA.equals(currentEdge.getNodeA()))
                    currentNode = currentEdge.getNodeB();
                else
                    currentNode = currentEdge.getNodeA();
            }

            if (visited.containsKey(currentNode) && visited.get(currentNode) > 10)
                continue;
            else if (visited.containsKey(currentNode))
                visited.put(currentNode, visited.get(currentNode) + 1);
            else
                visited.put(currentNode, 0);

            /*if(visited.contains(currentEdge))
                continue;

            visited.add(currentEdge);*/

            if (currentNode.equals(nodeB)) {
                paths.add(currentPath);
            }

            nextEdges = edges.get(currentNode);

            for (EdgeADT nextEdge : nextEdges) {

                newPath = new ArrayList<>(currentPath);

                if (!nextEdge.equals(currentEdge)) {
                    newPath.add(nextEdge);
                    agenda.add(newPath);
                }
            }
        }

        return paths;
    }

    /**
     * Overrides the default .toString() behaviour generating a user readable view of the graph
     *
     * @return Human readable string of the graph
     */
    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();

        for (NodeADT node : nodes.values()) {
            map.append(node.getLabel())
                    .append("\n");
            for (EdgeADT edge : edges.get(node)) {
                map.append("\t")
                        .append(edge.getLabel())
                        .append(" -> ");
                if (edge.getNodeA().equals(node)) {
                    map.append("\t")
                            .append(edge.getNodeB().getLabel())
                            .append("\n");
                } else {
                    map.append("\t")
                            .append(edge.getNodeA().getLabel())
                            .append("\n");
                }
                map.append("\n");
            }
        }

        return map.toString();
    }

    /**
     * Gets all nodes in a hashmap with the node id as the key
     *
     * @return Hashmap of nodes, k: node id, v: node
     */
    public Map<Integer, NodeADT> getNodes() {
        return nodes;
    }
}
