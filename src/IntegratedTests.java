import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests the integration of MetroMapParser, MultiGraph and BostonMetro
 */
public class IntegratedTests {

    BostonMetro bm = new BostonMetro();

    @BeforeEach
    public void setUp() {
        bm.readFile();
    }

    /**
     * Checks that OakGrove and Malden Center are connected
     */
    @Test
    public void isConnectedTrue() {

        NodeADT node1 = bm.metroSystem.getNode(1);
        NodeADT node2 =bm.metroSystem.getNode(2);
        System.out.println(bm.metroSystem.isConnected(node1, node2));
        Assertions.assertEquals(true, bm.metroSystem.isConnected(node1, node2));
    }

    /**
     * Checks that OakGrove and Broadway are not connected
     */
    @Test
    public void isConnectedFalse() throws Exception {

        NodeADT node1 = bm.metroSystem.getNode(1);
        NodeADT node2 =bm.metroSystem.getNode(60);
        Assertions.assertEquals(false, bm.metroSystem.isConnected(node1, node2));
    }

    /**
     * Checks there are 4 nodes spanning from State station: Haymarket, Aquarium, Downtown Crossing, Government Center
     */
    @Test
    public void checkGetConnected() {

        NodeADT node1 = bm.metroSystem.getNode(1); //1 node spanning
        NodeADT nod2 = bm.metroSystem.getNode(28); //4 nodes spanning
        NodeADT node3 = bm.metroSystem.getNode(98); //3 nodes spanning

        Assertions.assertEquals(1, bm.metroSystem.getConnected(node1).size());
        Assertions.assertEquals(4, bm.metroSystem.getConnected(nod2).size());
        Assertions.assertEquals(3, bm.metroSystem.getConnected(node3).size());
    }

    /**
     * Tests findPath algorithm integrated within the BostonMetro class.
     */
    @Test
    public void checkFindPath() {

        NodeADT node1 = bm.metroSystem.getNode(31); //Copley station
        NodeADT node2 =bm.metroSystem.getNode(16);  //Airport station
        String line;
        int lineChanges = 0;
        List<EdgeADT> path;

        path = bm.metroSystem.findPath(node1, node2); //Shortest weighted path

        line = path.get(0).getLabel();
        for(EdgeADT edge : path) {

            if(!line.equals(edge.getLabel())) {
                lineChanges++;
                line = edge.getLabel();
            }
        }

        Assertions.assertEquals(6, path.size());
        Assertions.assertEquals(3, lineChanges); // Cheapest path requires 3 line changes.

        lineChanges = 0;
        path = bm.findMinLineChanges(bm.metroSystem.findAllPaths(node1, node2)); //Minimum Line Changes Path

        line = path.get(0).getLabel();
        for(EdgeADT edge : path) {

            if(!line.equals(edge.getLabel())) {
                lineChanges++;
                line = edge.getLabel();
            }
        }

        Assertions.assertEquals(6, path.size());
        Assertions.assertEquals(1, lineChanges);
    }

    /**
     * Tests findPath algorithm integrated within the BostonMetro class.
     */
    @Test
    public void checkFindPathNull() {

        NodeADT node1 = bm.metroSystem.getNode(98);

        Assertions.assertNull(bm.metroSystem.findPath(node1, node1));
        Assertions.assertNull(bm.findMinLineChanges(bm.metroSystem.findAllPaths(node1, node1)));
    }
}
