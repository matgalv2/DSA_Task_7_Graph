import java.io.Serializable;
import java.util.ArrayList;

public class Vertex implements Serializable
{
    private int number;
    private ArrayList<Vertex> adjacentList;

    public Vertex(int number)
    {
        this.number =number ;
        adjacentList = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public ArrayList<Vertex> getAdjacentList() {
        return adjacentList;
    }

    public void setAdjacentList(ArrayList<Vertex> adjacentList) {
        this.adjacentList = adjacentList;
    }

    @Override
    public String toString()
    {
        return "Number: " + number + " Number of adjacent vertices: " + adjacentList.size();
    }
}