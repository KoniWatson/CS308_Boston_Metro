import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MultiGraphTests extends MultiGraph {

    GraphADT g = new MultiGraph();

    /**
     *
     */
    @Test
    public void addNodeTest() {
        //add node
        //get node
    }

    /**
     *
     */
    @Test
    void addEdgesTest() {
        //use is connected to check if edges successfully added
        //add same edge twice and use get connected to check its not added
    }

    /**
     *
     */
    @Test
    void findPathTest() {
        //build small weighted graph and check findPath returns shortest path
    }

    /**
     *
     */
    @Test
    void findPathNullTest() {
        //check path between 2 same nodes returns null
    }
}
