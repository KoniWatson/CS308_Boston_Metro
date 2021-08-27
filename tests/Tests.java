import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;

public class Tests extends MultiGraph {

    /**
     * Haymarket has 2 routes to Broadway: the orange line for 2 stops (Downtown Crossing)
     * Red line for 2 stops
     * --------OR--------
     * Haymarket on the green line park Street 2 stops red line for 3 stops.
     */
    @Test
    public void CheckingSearchAlgorithm() {
        BostonMetro.readFile();

        NodeADT node = BostonMetro.metroSystem.getNode(22);
        NodeADT node1 = BostonMetro.metroSystem.getNode(60);

        assertEquals(4, BostonMetro.metroSystem.findPath(node, node1).size());
    }

    /**
     * Checks that an edge has been successfully been created/added between two nodes
     */
    @Test
    public void checkAddEdges() {
        BostonMetro.readFile();
        Node nodeA = new Node(3, "Wonderland"), nodeB = new Node(61, "St.PaulStreet");
        Edge line = new Edge("orange", nodeA, nodeB, 5);

        BostonMetro.metroSystem.addEdge(line);

        Assertions.assertTrue(BostonMetro.metroSystem.isConnected(nodeA, nodeB));
    }


    /**
     * Checks there are 4 nodes spanning from State station: Haymarket,Aquarium,Downtown Crossing, Government Center
     */
    @Test
    public void checkGetConnected() {
        BostonMetro.readFile();

        NodeADT node = BostonMetro.metroSystem.getNode(28); //4 nodes spanning
        NodeADT node1 = BostonMetro.metroSystem.getNode(1); //1 node spanning
        NodeADT node2 = BostonMetro.metroSystem.getNode(98); //3 nodes spanning

        Assertions.assertEquals(4, BostonMetro.metroSystem.getConnected(node).size());
        Assertions.assertEquals(1, BostonMetro.metroSystem.getConnected(node1).size());
        Assertions.assertEquals(3, BostonMetro.metroSystem.getConnected(node2).size());
    }


    /**
     * Checks that OakGrove and Malden Center are connected
     */
    @Test
    public void isConnectedTrue() {
        BostonMetro.readFile();

        NodeADT node = BostonMetro.metroSystem.getNode(1);
        NodeADT node1 = BostonMetro.metroSystem.getNode(2);

        System.out.println(BostonMetro.metroSystem.isConnected(node, node1));
        Assertions.assertTrue(BostonMetro.metroSystem.isConnected(node, node1));
    }


    /**
     * Checks that OakGrove and Broadway arnt connected
     */
    @Test
    public void isConnectedFalse() {
        BostonMetro.readFile();

        NodeADT node = BostonMetro.metroSystem.getNode(1);
        NodeADT node1 = BostonMetro.metroSystem.getNode(60);

        Assertions.assertFalse(BostonMetro.metroSystem.isConnected(node, node1));
    }
}
