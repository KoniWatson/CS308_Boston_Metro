/**
 * Edge.java
 * Implementation of EdgeADT for a weighted edge connecting two nodes with a label
 */
public class Edge implements EdgeADT {

    private String label;
    private NodeADT nodeA;
    private NodeADT nodeB;
    private int weight;

    /**
     * Constructor for the Edge, takes a label, 2 nodes and a weight.
     * N.B nodeA and nodeB (origin, destination) are interchangeable in this implementation
     *
     * @param label  The edge's readable label
     * @param nodeA  The origin node of the edge
     * @param nodeB  The destination node of the edge
     * @param weight The weight of the edge
     */
    public Edge(String label, NodeADT nodeA, NodeADT nodeB, int weight) {
        this.label = label;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;
    }

    /**
     * Getter for edges label
     *
     * @return edge's label
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Getter for the origin node of the edge
     *
     * @return edge's origin node
     */
    @Override
    public NodeADT getNodeA() {
        return nodeA;
    }

    /**
     * Getter for the destination node of the edge
     *
     * @return edge's destination node
     */
    @Override
    public NodeADT getNodeB() {
        return nodeB;
    }

    /**
     * Getter for the weight of the edge
     *
     * @return edge's weight
     */
    @Override
    public int getWeight() {
        return weight;
    }
}
