/**
 * Implementation of NodeADT
 * nodeA and nodeB are interchangeable in this implementation
 */
public class Node implements NodeADT {

    private String name;
    private int id;

    /**
     * Constructor for a node, node requires an id and name
     *
     * @param id   The node's label
     * @param name The node's name
     */
    public Node(int id, String name) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter for the name of the node
     *
     * @return node's name
     */
    @Override
    public String getLabel() {
        return name;
    }

    /**
     * Getter for the id of the node
     *
     * @return node's id
     */
    @Override
    public int getId() {
        return id;
    }
}
