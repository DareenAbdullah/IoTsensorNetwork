import java.util.*;
/**AdjacentListHead Class.
 **/
public class AdjacencyListHead {
    /**Private node variable.*/
    private IoTSensorNode node;
    /**Private adjacencyList variable.*/
    private LinkedList<Adjacent> adjacencyList;
    
    /**Constructor.
     * @param node IoTSensorNode*/
    public AdjacencyListHead(IoTSensorNode node){
        this.node=node;
        this.adjacencyList=new LinkedList<Adjacent>(); 
    }

    /**Constructor.
     * @param node IoTSensorNode
     * @param adjacencyList a linked list of the adjacent nodes*/
    public AdjacencyListHead(IoTSensorNode node,LinkedList<Adjacent> adjacencyList){
        this.node=node;
        this.adjacencyList=adjacencyList;
    }

    /**Setter method.
     * @param node IoTSensorNode*/
    public void setIoTSensorNode(IoTSensorNode node){
        this.node=node;
    }

    /**Setter method.
     * @param adjacencyList the linked list*/
    public void setAdjacencyList(LinkedList<Adjacent> adjacencyList){
        this.adjacencyList=adjacencyList;
    }

    /**Getter method.
     * @return IoTSensorNode node*/
    public IoTSensorNode getIoTSensorNode(){
        return node;
    }
    
    /**Getter method.
     * @return LinkedList list*/
    public LinkedList<Adjacent> getAdjacencyList(){
        return adjacencyList;
    }
    
}
