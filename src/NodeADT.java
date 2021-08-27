/**
 * Interface for creating nodes of a graph
 * N.B nodeA and nodeB (origin, destination) are only interchangeable in a non-digraph implementation
 */
public interface NodeADT {
    /**
     * Getter for the label of the node
     *
     * @return node's label
     */
    String getLabel();

    /**
     * Getter for the id of the node
     *
     * @return node's id
     */
    int getId();
}
