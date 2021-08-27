import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

/**
 * Contains the entry point to the program and user interaction methods
 */
public class BostonMetro {

    //Changed to protected for testing
    protected static GraphADT metroSystem;
    private static List<String> stations = new ArrayList<>();

    /**
     * Generates a graph from 'bostonmetro.txt'
     */
    public static void readFile() {
        try {
            MetroMapParser fileReader = new MetroMapParser("bostonmetro.txt");
            metroSystem = fileReader.generateGraphFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read one char of input from the user
     *
     * @return one character from the user.
     */

    private static char getCharInput() {

        Scanner sc = new Scanner(System.in);
        char c;

        c = sc.next().charAt(0);  //Getting the users decision
        return c;
    }

    /**
     * Finds the id of a station from the users input of a station name
     *
     * @return the id of the found station
     */
    private static int getInput() {
        Map<Integer, NodeADT> allNodes = metroSystem.getNodes();
        Scanner sc = new Scanner(System.in);
        int stationID;

        for (int i : allNodes.keySet()) {
            stations.add(allNodes.get(i).getLabel());
        }

        while (true) {

            String stationInput = null;
            stationInput = sc.nextLine();

            if (stations.contains(stationInput)) {
                //Looping through all nodes names to find and retrieve the NodeADT object.
                for (int i : allNodes.keySet()) {
                    if (stationInput.equals(allNodes.get(i).getLabel()) && stationInput.equals("St.PaulStreet")) {
                        //Switching depending upon the user input and setting the stationID to the correct station
                        System.out.println("Which St.PaulStreet station do you mean? Green Line B (b) or Green Line C (c): ");
                        switch (getCharInput()) {
                            case 'b':
                                stationID = 38;
                                break;
                            case 'c':
                                stationID = 61;
                                break;
                            default:
                                System.out.println("Invalid input! Setting to default - St.PaulStreet Green Line B");
                                stationID = 38;
                                break;
                        }
                        return stationID;

                    } else if (stationInput.equals(allNodes.get(i).getLabel())) {
                        stationID = i;
                        return stationID;
                    }
                }
            } else {
                System.out.println("This station does not exist on the Boston Metro System, please try again");
            }
        }
    }

    /**
     * Prints out route given from the findPath() method in and easy way for the user to read.
     * This is done but printing the colour of the lines, how many stops to take and the swapping
     * line station.
     */
    public static void getRoute() {
        List<EdgeADT> path = null;
        EdgeADT nextEdge;
        NodeADT node1, node2;
        String nextNode;
        int stop = 0;
        boolean done = false;

        //Getting the users input for the starting and finishing locations.
        System.out.println("Please enter your start location: ");
        node1 = metroSystem.getNode(getInput());
        System.out.println("Please enter your destination location: ");
        node2 = metroSystem.getNode(getInput());

        System.out.println("Enter '0' if you would like to find the cheapest route, or '1' if you want to find the quickest route:");
        switch(getCharInput()) {
            case '0':
                path = metroSystem.findPath(node1, node2);  //Getting the actual path based off the user inputs
                break;

            case '1':
                path = findMinLineChanges(metroSystem.findAllPaths(node1, node2));
                break;

            default:
                System.out.println("Invalid Input, Setting to Default - Finding Quickest Route: \n");
                path = findMinLineChanges(metroSystem.findAllPaths(node1, node2));
                break;
        }

        if (path == null) {
            System.out.println("You are already at your destination.");
            return;
        }

        System.out.println("==============================================================================");
        System.out.println("-> ||| To get from " + node1.getLabel().toUpperCase() + " ---> " + node2.getLabel().toUpperCase() + " follow the following route ||| <- ");
        System.out.println("============================================================================== \n");

        nextEdge = path.get(0);
        if (node1.equals(nextEdge.getNodeA()))
            nextNode = stations.get(nextEdge.getNodeB().getId() - 1);
        else
            nextNode = stations.get(nextEdge.getNodeA().getId() - 1);

        System.out.println("Starting at -> | " + node1.getLabel().toUpperCase() + " | -> station take the -> | " + path.get(0).getLabel().toUpperCase() + " | -> line towards -> | " + nextNode.toUpperCase() + " | -> station \n");
        for (int i = 0; i < path.size() - 1; i++) { //Looping through all the edges through returned by the findPath() method
            nextEdge = path.get(i);
            if (node1.equals(nextEdge.getNodeA()))
                nextNode = stations.get(nextEdge.getNodeB().getId() - 1);
            else
                nextNode = stations.get(nextEdge.getNodeA().getId() - 1);

            if (path.get(i).getLabel().equals(path.get(i + 1).getLabel()) && !done) {   //Done check so if on the same line, printing wont happen multiple times
                //Loop to find one many stops are to be taken on the same line
                for (EdgeADT aPath : path) {
                    if (path.get(i).getLabel().equals(aPath.getLabel())) {
                        stop++;
                    }
                }
                System.out.println("Stay on line -> | " + path.get(i).getLabel().toUpperCase() + " | -> for -> | " + stop + " | -> stop(s) \n");
                stop = 0;   //Re-setting stop to be reused
                done = true;    //Setting the done check so repeated printing doesn't occur.
            } else if (!path.get(i).getLabel().equals(path.get(i + 1).getLabel())) {
                System.out.println("Change line from -> | " + path.get(i).getLabel().toUpperCase() + " | -> to -> | " + path.get(i + 1).getLabel().toUpperCase() +
                        " | -> at -> | " + nextNode.toUpperCase() + " | <- station \n");
                done = false;
            }
        }
        System.out.println("You have reached your destination of -> | " + node2.getLabel().toUpperCase() + " | -> station \n");
        System.out.println("============================================================================== \n");
    }

    /**
     * Finds and returns the path with the least line changes from a list of potential paths
     *
     * @param paths A List of Lists of Edges - i.e. a list of paths
     * @return The path with the lowest number of line changes
     */
    public static List<EdgeADT> findMinLineChanges(List<List<EdgeADT>> paths) {

        if (paths == null)
            return null;

        List<EdgeADT> path = null;
        int minChanges = Integer.MAX_VALUE;
        int minSize = Integer.MAX_VALUE;
        int lineChanges;
        String line;

        for (List<EdgeADT> altPath : paths) {
            lineChanges = 0;
            line = altPath.get(0).getLabel();
            for (EdgeADT edge : altPath) {
                if (!edge.getLabel().equals(line)) {
                    line = edge.getLabel();
                    lineChanges++;
                }
            }
            if (lineChanges < minChanges) {

                path = altPath;
                minChanges = lineChanges;
                minSize = altPath.size();

            } else if (lineChanges == minChanges && altPath.size() < minSize) {

                path = altPath;
                minSize = altPath.size();
            }
        }

        return path;
    }

    /**
     * The entry point to the program, reads the graph from the file,
     * displays it to the user and asks for input,
     * finds a path between the chosen stations and shows instructions to the user
     *
     * @param args No args are required to run
     */
    public static void main(String[] args) {

        readFile();
        System.out.println(metroSystem);
        getRoute();
    }
}
