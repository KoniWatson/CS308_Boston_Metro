/**
 * Interface for creating the edges of a graph
 */
public interface EdgeADT {

    /**
     * Getter for edges label
     *
     * @return edge's label
     */
    String getLabel();

    /**
     * Getter for the origin node of the edge
     *
     * @return edge's origin node
     */
    NodeADT getNodeA();

    /**
     * Getter for the destination node of the edge
     *
     * @return edge's destination node
     */
    NodeADT getNodeB();

    /**
     * Getter for the weight of the edge
     *
     * @return edge's weight
     */
    int getWeight();
}
