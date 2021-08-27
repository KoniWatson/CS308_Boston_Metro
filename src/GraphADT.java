import java.util.Map;
import java.util.List;

/**
 * Interface for creating a generic MultiGraph
 */
public interface GraphADT {

    /**
     * Adds a node to the graph
     *
     * @param node The node to be added to the graph
     */
    void addNode(NodeADT node);

    /**
     * Getter for a node from an id
     *
     * @param id The id of the node to find
     * @return The NodeADT object of the node id provided
     */
    NodeADT getNode(int id);

    /**
     * Adds an edge to the graph
     *
     * @param edge The edge to be added to the graph
     */
    void addEdge(EdgeADT edge);

    /**
     * Checks if two nodes are directly connected by one edge
     *
     * @param nodeA One node to check for a connection
     * @param nodeB The other node to check for a connection
     * @return boolean value if two nodes are connected by an edge
     */
    boolean isConnected(NodeADT nodeA, NodeADT nodeB);

    /**
     * Gets the related edges to a given node
     *
     * @param node The node to find related edges
     * @return List of edges connected to the given node
     */
    List<EdgeADT> getConnected(NodeADT node);

    /**
     * Find a path between two given nodes
     *
     * @param nodeA The users current location
     * @param nodeB The users desired location
     * @return A list of edges in the order of the path to be taken
     */
    List<EdgeADT> findPath(NodeADT nodeA, NodeADT nodeB);

    /**
     * Find all possible paths between two given nodes
     *
     * @param nodeA The users current location
     * @param nodeB The users desired location
     * @return A list of list of edges. i.e. a list of paths
     */
    List<List<EdgeADT>> findAllPaths(NodeADT nodeA, NodeADT nodeB);

    /**
     * Gets all nodes in a hashmap with the node id as the key
     *
     * @return Hashmap of nodes, k: node id, v: node
     */
    Map<Integer, NodeADT> getNodes();
}
