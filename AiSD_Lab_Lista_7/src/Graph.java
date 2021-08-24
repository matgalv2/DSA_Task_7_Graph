import java.io.*;
import java.util.*;

public class Graph implements Serializable
{
//    private HashMap<Vertex, ArrayList<Vertex>> vertices;
    private ArrayList<Vertex> vertices;
//    private int [][] edges;

    public Graph()
    {
        vertices = new ArrayList<>();
    }

    public Graph(String fileName) throws IOException, ClassNotFoundException
    {
        Graph graph = read(fileName);
        vertices = graph.vertices;
    }

    public Graph(int [][] adjacentMatrix, String fileName)
    {
        for (int i = 0; i < adjacentMatrix.length; i++)
        {
            vertices.add(new Vertex(i));
        }

        for (int i = 0; i < adjacentMatrix.length; i++)
        {
            for (int j = i + 1; j < adjacentMatrix.length; j++)
            {
                if (adjacentMatrix[i][j] == 1)
                {
                    vertices.get(i).getAdjacentList().add(vertices.get(j));
                    vertices.get(j).getAdjacentList().add(vertices.get(i));
                }
            }
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Vertex getVertex(int number)
    {
        for(Vertex vertex : vertices)
        {
            if (vertex.getNumber() == number)
            {
                return vertex;
            }
        }
        return null;
    }


    public void addVertex(int number)
    {
        for(Vertex vertex : vertices)
        {
            if (vertex.getNumber() == number)
            {
                System.out.println("Vertex with that id is already created.");
                return;
            }
        }

        vertices.add(new Vertex(number));
    }

    public void addEdge(int number1, int number2)
    {
        Vertex vertex1 = getVertex(number1);
        Vertex vertex2 = getVertex(number2);
//        for(Vertex vertex : vertex2.getAdjacentList())
        for(Vertex vertex : vertex1.getAdjacentList())
        {
//            if(vertex.equals(vertex1))
            if(vertex.equals(vertex2))
            {
                System.out.println("The edge is already created.");
                return;
            }
        }

        vertex1.getAdjacentList().add(vertex2);

    }

    public void addEdge2(int number1, int number2)
    {
        Vertex vertex1 = getVertex(number1);
        Vertex vertex2 = getVertex(number2);
        for(Vertex vertex : vertex1.getAdjacentList())
        {
//            if(vertex.equals(vertex1))
            if(vertex.equals(vertex2))
            {
                System.out.println("The edge is already created.");
                return;
            }
        }

        vertex1.getAdjacentList().add(vertex2);
        vertex2.getAdjacentList().add(vertex1);
    }

    public void write(String fileName) throws IOException
    {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
        output.writeObject(this);
        output.close();
    }

    public Graph read(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(fileName)));
        Graph graph = (Graph) input.readObject();
        input.close();
        return graph;
    }

    public void findCycles()
    {
        ArrayList<ArrayList<Integer>>cyclesList = new ArrayList<>();
        boolean [] visited;
//        while(!allTrue(visited))
//        {

            for (int i = 0; i < vertices.size(); i++)
            {
                Stack<Integer> stack = new Stack<>();
                ArrayList<Integer> list = new ArrayList<>();

                visited = new boolean[vertices.size()];
                stack.push(-1);
                if(!DFSfindCycle(vertices.get(i),vertices.get(i),stack,visited))
                {
                    stack.pop();
                }
                else
                {
//                    System.out.println(i);
                    list.add(i);
                    while(!stack.isEmpty())
                    {
//                        if(stack.peek() != -1)
//                        {
//                            list.add(stack.peek());
//                        }
                        int tmp = stack.pop();
                        if(tmp != -1)
                        {
//                            System.out.println(tmp);
                            list.add(tmp);
                        }
                    }
//                    System.out.println();
                }
                if(!list.isEmpty())
                {
                    cyclesList.add(list);
                }
                ///////////////////////////////////////////////////////////
                changeList(list);
            }
            printCycles(cyclesList);
//        }
    }

    private boolean DFSfindCycle(Vertex startingVertex, Vertex currentVertex, Stack<Integer> stack, boolean [] visited)
    {
        visited[vertices.indexOf(currentVertex)] = true;

        for(Vertex vertex : vertices.get(vertices.indexOf(currentVertex)).getAdjacentList())
        {
            if(vertex.getAdjacentList().size() > 1)
            {
                if(stack.peek() != vertices.indexOf(vertex))
                {
                    stack.push(vertices.indexOf(currentVertex));
                    if(vertex.equals(startingVertex))
                        return true;

                    if(!visited[vertices.indexOf(vertex)] && DFSfindCycle(startingVertex, vertex, stack, visited))
                        return true;

                    stack.pop();
                }
            }

        }
        return false;
    }

    private boolean allTrue(boolean [] visited)
    {
        for(boolean b : visited)
        {
            if(!b)
            {
                return false;
            }
        }
        return true;
    }

    private boolean [] setAllFalse(boolean [] visited)
    {
        for(boolean b : visited)
        {
            b = false;
        }
        return visited;
    }

    public void DFSrec(int number, boolean [] visited)
    {
        visited[number] = true;

        System.out.println(vertices.get(number));

        for(Vertex vertex : vertices.get(number).getAdjacentList())
        {
            if(!visited[vertices.indexOf(vertex)])
            {
                DFSrec(vertex.getNumber(), visited);
            }
        }
    }

    public void DFS(int number)
    {
        if(number>= vertices.size())
        {
            return;
        }

        boolean [] visited = new boolean[vertices.size()];
        DFSrec(number,visited);
    }

    public int [][] createAdjacentMatrix()
    {
        int [][] matrix = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = i ; j < matrix[i].length; j++)
            {
                if(contains(vertices.get(i),vertices.get(j)))
                {
                    matrix[i][j] = 1;
                    matrix[j][i] = 1;
                }
                else
                {
                    matrix[i][j] = 0;
                    matrix[j][i] = 0;
                }
            }
        }
        return matrix;
    }


    private boolean contains(Vertex vertexList, Vertex vertexSearched)
    {
        for (Vertex vertex : vertexList.getAdjacentList())
        {
            if(vertex.equals(vertexSearched))
            {
                return true;
            }
        }
        return false;
    }

    public void printCycles(ArrayList<ArrayList<Integer>>cyclesList)
    {
        for (int i = 0; i < cyclesList.size(); i++)
        {
            for (int j = 0; j <cyclesList.size() ; j++)
            {

                if (equals(cyclesList.get(i), cyclesList.get(j)) && i != j)
                    cyclesList.remove(j);
            }
        }

        for (int i = 0; i < cyclesList.size(); i++)
        {
            System.out.println((i+1) + ". cycle: ");
            for (int j = 1; j < cyclesList.get(i).size(); j++)
            {
                printEdge(cyclesList.get(i).get(j-1),cyclesList.get(i).get(j));
            }
            System.out.println();

        }
    }

    public void printEdge(int number1, int number2)
    {
        Vertex vertex1 = getVertex(number1);
        Vertex vertex2 = getVertex(number2);

        System.out.println("[" + vertex1.getNumber() + ", " + vertex2.getNumber() + "]");
    }

    public boolean equals(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        ArrayList<Integer> list3 = (ArrayList<Integer>) list1.clone();
        ArrayList<Integer> list4 = (ArrayList<Integer>) list2.clone();

        list3.remove(list3.size()-1);
        list4.remove(list4.size()-1);

        Collections.sort(list3);
        Collections.sort(list4);

        if(list3.equals(list4))
            return true;
        return false;
    }

    public boolean existInRegister(Stack<Integer> stack, ArrayList<ArrayList<Integer>> list)
    {
        ArrayList<Integer> listWithCycle = new ArrayList<>();
        while(!stack.isEmpty())
        {
            listWithCycle.add(stack.pop());
        }
        listWithCycle.add(-8);
        for (int i = 0; i < list.size(); i++)
        {
            if(equals(list.get(i),listWithCycle))
            {
                return true;
            }
        }
        return false;
    }

    public void changeList(ArrayList<Integer> listWithCycle)
    {
        for (int i = listWithCycle.size() - 1; i >= 1; i--)
        {
            vertices.get(listWithCycle.get(i)).getAdjacentList().remove(vertices.get(listWithCycle.get(i-1)));
            vertices.get(listWithCycle.get(i)).getAdjacentList().add(vertices.get(listWithCycle.get(i-1)));
        }
    }

    public void writeAdjacentMatrix(int [][] matrix, String fileName) throws IOException
    {
        FileWriter fileWriter = new FileWriter(new File(fileName));
//        fileWriter.write(matrix.length+ "\n");
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                fileWriter.write(matrix[i][j] + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public int[][] readAdjacentMatrix(String fileName) throws IOException
    {
        Scanner scanner = new Scanner(fileName);
//        int length = Integer.parseInt(scanner.nextLine());

        ArrayList<String> str = new ArrayList<>();
        while(scanner.hasNextLine())
        {
            str.add(scanner.nextLine());
        }

        int [][] adjacentMatrix = new int [str.size()][str.size()];

        for (int i = 0; i < str.size(); i++)
        {

            String st1 = str.get(i).trim();
            for (int j = 0; j < str.size(); j++)
            {
                adjacentMatrix[i][j] = Integer.parseInt(String.valueOf(st1.charAt(j)));
            }
        }

        scanner.close();
        return adjacentMatrix;
    }



}