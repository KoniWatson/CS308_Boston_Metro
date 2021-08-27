import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Parser for generating a GraphADT from a text file
 */
public class MetroMapParser {

    private BufferedReader fileInput;

    /**
     * Constructor, requires the path to the file to be parsed
     *
     * @param filename The path to the file that has to be parsed
     * @throws IOException Thrown when there is a problem reading from the specified path
     */
    public MetroMapParser(String filename) throws IOException {
        fileInput = new BufferedReader(new FileReader(filename));
    }

    /**
     * Generates and returns a graph object from the content of the file passed in the constructor
     *
     * @return The GraphADT representation of the file content
     * @throws IOException      Thrown only when the input file exists but has no content
     * @throws BadFileException Thrown when there is a formatting error within the file
     */
    public GraphADT generateGraphFromFile() throws IOException, BadFileException {

        GraphADT graph = new MultiGraph();
        String line = fileInput.readLine();
        StringTokenizer st;
        int stationID, outboundID, inboundID, weight;
        String stationName, lineName;
        NodeADT newNode;

        List<NodeADT> nodes = new ArrayList<>();
        Map<NodeADT, List<String>> nodeLines = new HashMap<>();
        Map<NodeADT, List<Integer>> nodeConnections = new HashMap<>();
        List<String> lines;
        List<Integer> connections;

        while (line != null) {
            st = new StringTokenizer(line);

            if (!st.hasMoreTokens()) {
                line = fileInput.readLine();
                continue;
            }
            stationID = Integer.parseInt(st.nextToken());
            if (!st.hasMoreTokens()) {
                throw new BadFileException("no station name");
            }
            stationName = st.nextToken();

            if (!st.hasMoreTokens()) {
                throw new BadFileException("station is on no lines");
            }
            newNode = new Node(stationID, stationName);
            lines = new ArrayList<>();
            connections = new ArrayList<>();

            while (st.hasMoreTokens()) {

                lineName = st.nextToken();
                lines.add(lineName);

                if (!st.hasMoreTokens()) {
                    throw new BadFileException("poorly formatted line info");
                }

                outboundID = Integer.parseInt(st.nextToken());
                connections.add(outboundID);

                if (!st.hasMoreTokens()) {
                    throw new BadFileException("poorly formatted adjacent stations");
                }

                inboundID = Integer.parseInt(st.nextToken());
                connections.add(inboundID);
            }

            graph.addNode(newNode);
            nodes.add(newNode);
            nodeLines.put(newNode, lines);
            nodeConnections.put(newNode, connections);

            line = fileInput.readLine();
        }

        int index;
        for (NodeADT n : nodeConnections.keySet()) {
            index = 0;
            for (String l : nodeLines.get(n)) {
                connections = nodeConnections.get(n);
                weight = assignEdgeWeight(l);

                inboundID = connections.get(index);
                if (inboundID != 0)
                    graph.addEdge(new Edge(l, n, nodes.get(inboundID - 1), weight));
                index++;

                outboundID = connections.get(index);
                if (outboundID != 0)
                    graph.addEdge(new Edge(l, n, nodes.get(outboundID - 1), weight));
                index++;
            }
        }

        return graph;
    }

    /**
     * Returns what the weight should be for a given line based on the line that edge is a part of
     *
     * @param line The line colour
     * @return The respective weight
     */
    private int assignEdgeWeight(String line) {

        int weight;

        if (line.equals("Orange")) {
            weight = 20;
        } else if (line.equals("Blue")) {
            weight = 15;
        } else if (line.equals("Mattapan")) {
            weight = 10;
        } else if (line.equals("Green")) {
            weight = 25;
        } else if (line.contains("Green")) {
            weight = 5;
        } else {
            weight = 10;
        }

        return weight;
    }
}
