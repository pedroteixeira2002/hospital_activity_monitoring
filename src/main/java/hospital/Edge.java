package hospital;

/**
 * The Edge class represents an edge in the graph.
 * It contains the weight of the edge and the rooms it connects.
 * The weight represents the distance between the rooms.
 * The rooms are represented by their id.
 * The edge is bidirectional, so it connects two rooms.
 *
 */
public class Edge {
    /**
     * The weight of the edge
     */
    private final double weight;
    /**
     * The first room of the edge
     */
    private final int room1;
    /**
     * The second room of the edge
     */
    private final int room2;

    /**
     * Constructor of the class Edge
     * @param room1 the first room
     * @param room2 the second room
     * @param weight the weight of the edge
     */
    public Edge(int room1, int room2, double weight) {
        this.weight = weight;
        this.room1 = room1;
        this.room2 = room2;
    }

    /**
     * Get the weight of the edge
     * @return the weight of the edge
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Get the first room of the edge
     * @return the first room of the edge
     */
    public int getRoom1() {
        return room1;
    }

    /**
     * Get the second room of the edge
     * @return the second room of the edge
     */
    public int getRoom2() {
        return room2;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", room1=" + room1 +
                ", room2=" + room2 +
                '}';
    }
}
