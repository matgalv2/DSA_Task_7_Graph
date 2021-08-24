import java.io.IOException;

public class Main
{
    public static void main(String[]args) throws IOException, ClassNotFoundException {
        Graph graph = new Graph();


        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);



        graph.addEdge2(1,0);
        graph.addEdge2(1,2);
        graph.addEdge2(1,3);
        graph.addEdge2(2,4);
        graph.addEdge2(3,4);
        graph.addEdge2(4,5);
        graph.addEdge2(5,6);
        graph.addEdge2(1,7);
        graph.addEdge2(1,8);
//        graph.addEdge2(0,8);
//        graph.addEdge2(0,7);
        graph.addEdge2(3,7);





//        graph.DFS(0);
        // w findCycles dodałem do drukowanych jeden, bo wierzchołki zaczynają się od 1, ale lista i tablica zaczynają się od indeksu 0
        graph.findCycles();
        graph.writeAdjacentMatrix(graph.createAdjacentMatrix(), "AdjacentMatrix.txt");


//        graph.write("graph.txt");
//

//        Graph graphRead = new Graph("graph.txt");
//        graphRead.findCycles();


        Graph g = new Graph();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);

        g.addEdge2(0,1);
        g.addEdge2(1,2);
        g.addEdge2(2,3);
        g.addEdge2(1,3);
        g.addEdge2(3,0);
        g.addEdge2(2,0);



//        g.addEdge2(2,3);
//        g.addEdge2(0,1);
//        g.addEdge2(1,3);
//        g.addEdge2(1,2);
//        g.addEdge2(3,0);

//        g.findCycles();


    }
}
