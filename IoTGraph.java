import java.util.*;
/**IoT Graph Class.
 * @author Dareen Abdullah*/
public class IoTGraph {

    /**Private nodesList arrayList of adjacencyListHead objects.*/
    private ArrayList<AdjacencyListHead> nodesList;
    
    /**Constructor.
     * @param nodesList an arrayList of adjacencyListHead objects*/
    public IoTGraph(ArrayList<AdjacencyListHead> nodesList){
        this.nodesList=nodesList;
        //This method is complete
    }

    /**Getter Method.
     * @return an arrayList of adjacencyListHead objects*/
    public ArrayList<AdjacencyListHead> getNodesList(){
        return nodesList;
        //This method is complete
    }
    
    /**Getter for the num of nodes.
     * @return int size*/
    public int getNumNodes(){
        //Implement this method
        //Should return the number of nodes in the Graph
        return nodesList.size();
    }

    /**Getter for the number of edges in the graph.
     * @return number of edges*/
    public int getNumLinks(){
        //Implement this method
        //returns the number of edges in the graph. Remember this is an undirected graph
        //BIG O COMPLEXITY: O(N)
        int sum = 0;
        for (int i = 0; i < nodesList.size(); i++){
            sum += nodesList.get(i).getAdjacencyList().size();
        }

        return sum/2;
    }

    /**Add an IoTSensorNode to the graph with an empty adjacencyListHead.
     * @param id of the node
     * @param name of the node
     * @param coordinateX of the node
     * @param coordinateY of the node*/
    public void addIoTSensorNode(int id, String name, double coordinateX, double coordinateY){   
        //Implement this method
        //Adds a new node to the graph. The node is represented by the IoTSensorNode class having id, name, x_coordinate, and y_coordinatte instance variables
        //You should check if the nodes already exists in the graph. If this is the case throw an IllegalArgumentException
        //BIG O IS O(N)
        IoTSensorNode node = new IoTSensorNode(id, name, coordinateX, coordinateY);
        
        for (int i = 0; i < getNumNodes(); i++){
            if (nodesList.get(i).getIoTSensorNode().equals(node)){
                throw new IllegalArgumentException(); 
            }
        }

        AdjacencyListHead adjacencyListHead = new AdjacencyListHead(node);
        nodesList.add(adjacencyListHead);
	}

    /**Helper method: returns the index if found else -1.
     * @param node IoTSensorNode
     * @return int index*/
    private int nodeExists(IoTSensorNode node){
        //BIG O COMPLEXITY IS O(N) 

        int index = 0;
        int count = 0;

        for (int i = 0; i < getNumNodes(); i++){
            if (nodesList.get(i).getIoTSensorNode().equals(node)){
                index = i;
                count += 1;
                break;
            }
        }
        if (count == 1){
            return index;
        }
        else{
            return -1;
        }
    }
    
    /**Add a link between two nodes.
     * @param node1 IoTSensorNode
     * @param node2 IoTSensorNode
     * @param weight of the edge*/
    public void addLink(IoTSensorNode node1, IoTSensorNode node2, double weight){   
        //Implement this method
        //adds a link in the graph between two sensor nodes node1 and node2.
        //You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException
        //BIG O COMPLEXITY IS O(N)
        if (node1 == null || node2 == null){
            throw new IllegalArgumentException();
        }
        int index1 = nodeExists(node1);
        int index2 = nodeExists(node2);
        
        //Saves indexes and ensures that both nodes exist in the graph
        if (index1 < 0 || index2 < 0){
            throw new IllegalArgumentException();
        }

        //Check if the edge already exists
        LinkedList<Adjacent> adjacencyList1 = nodesList.get(index1).getAdjacencyList();
        for (int j = 0; j < adjacencyList1.size(); j++){
            if (adjacencyList1.get(j).getNeighbor().equals(node2)){
                throw new IllegalArgumentException();
            }
        }
        
        //Check if the edge already exists
        LinkedList<Adjacent> adjacencyList2 = nodesList.get(index2).getAdjacencyList();
        for (int j = 0; j < adjacencyList2.size(); j++){
            if (adjacencyList2.get(j).getNeighbor().equals(node1)){
                throw new IllegalArgumentException();
            }
        }

        Adjacent newEdge = new Adjacent(node2, weight);
        nodesList.get(index1).getAdjacencyList().add(newEdge);


        Adjacent newEdge2 = new Adjacent(node1, weight);
        nodesList.get(index2).getAdjacencyList().add(newEdge2);
    }

    /**Deletes a specified node.
     * @param node to be deleted*/
    public void deleteIoTSensorNode(IoTSensorNode node){
        //Implement this method
        //deletes a particular sensor node from the IoTGraph. Remember to delete all edges containing it from the different adjacency lists
        //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        
        //Big O complexity is O(N^2)

        if (node == null){
            throw new IllegalArgumentException();
        }
        int index = nodeExists(node);

        //node is not in graph
        if (index < 0){
            throw new IllegalArgumentException();
        }

        LinkedList<Adjacent> adjacencyList = nodesList.get(index).getAdjacencyList();
        int n = adjacencyList.size();
        for (int j = 0; j < n; j++){
            removeLink(node, adjacencyList.get(0).getNeighbor());
        }
        nodesList.remove(index);

    }
    
    /**Removes a link between two nodes; deleted from both nodes' adjancencyLists.
     * @param node1 IoTSensorNode
     * @param node2 IoTSensorNode*/
    public void removeLink(IoTSensorNode node1, IoTSensorNode node2){   
        //Implement this method
        //deletes a link between two sensor nodes in the IoTGraph. 
        //You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException
        
        //BIG O IS O(N)

        if (node1 == null || node2 == null){
            throw new IllegalArgumentException();
        } 
        int index = 0;
        int indexList = 0;
        int index2 = 0;
        int indexList2 = 0;
        int count = 0;
        int count2 = 0;

        //CHANGE THESE TO PRIVATE HELPER

        for (int i = 0; i < getNumNodes(); i++){
            if (nodesList.get(i).getIoTSensorNode().equals(node1)){
                index = i;
                count+=1;
            }
            else if (nodesList.get(i).getIoTSensorNode().equals(node2)){
                index2 = i;
                count+=1;
            }
        }

        if (count != 2){
            throw new IllegalArgumentException();
        }

        LinkedList<Adjacent> adjacencyList = nodesList.get(index).getAdjacencyList();
        for (int j = 0; j < adjacencyList.size(); j++){
            if (adjacencyList.get(j).getNeighbor().equals(node2)){
                indexList = j;
                count2+=1;
            }
        }

        LinkedList<Adjacent> adjacencyList2 = nodesList.get(index2).getAdjacencyList();
        for (int j = 0; j < adjacencyList2.size(); j++){
            if (adjacencyList2.get(j).getNeighbor().equals(node1)){
                indexList2 = j;
                count2+=1;
            }
        }
        
        if (count2 != 2){
            throw new IllegalArgumentException();
        }

        nodesList.get(index).getAdjacencyList().remove(indexList);
        nodesList.get(index2).getAdjacencyList().remove(indexList2);


    }

    /**Getter method for adjacencyList.
     * @param node IoTSensorNode
     * @return AdjacencyList of the node*/
    public LinkedList<Adjacent> getAdjacents(IoTSensorNode node){
        //Implement this method
        //returns a LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link
        //BIG O(N)

        if (node == null){
            throw new IllegalArgumentException();
        }

        int index = nodeExists(node);

        if (index < 0){
            throw new IllegalArgumentException();
        }

        return nodesList.get(index).getAdjacencyList();     
    }

    /**Getter method for index of a specified node.
     * @param node IoTSensorNode
     * @return int index*/
    int getNodeIndex(IoTSensorNode node){ 
        //Implement this method
        //returns the index in the nodesList ArrayList of a particular node.
        //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        //BIG O(N)
        if (node == null){
            throw new IllegalArgumentException();
        }
        int index = nodeExists(node);

        if (index < 0){
            throw new IllegalArgumentException();
        }

        return index;
    }

    /**Return the degree of a node.
     * @param node IoTSensorNode
     * @return int degree*/
    public int degree(IoTSensorNode node){ 
        //Implement this method
        //returns the number of adjacent nodes of a particular sensor node
        ////You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
        //O(n)
        int index = getNodeIndex(node);

        return nodesList.get(index).getAdjacencyList().size();
    }
    
    /**The greatest degree in the graph.
     * @return int max degree*/
    public int getGraphMaxDegree(){
        //Implement this method
        //returns the maximum number of adjacent nodes connected to a particular node
        int max = 0;
        int current = 0;
        for (int i = 0; i < getNumNodes(); i++){
            current = degree(nodeFromIndex(i));
            if (current > max){
                max = current;
            }
        }
        return max;
	}

    //POSSIBLE NEGATIVE INDEX OR OUT OF RANGE?
    /**Retrieve the node from an index.
     * @param index in the graph
     * @return node IoTSensorNode*/
    public IoTSensorNode nodeFromIndex(int index){
        //Implement this method
        //returns an IoTSensorNode object from the index of the node in nodesList ArrayList
        return nodesList.get(index).getIoTSensorNode();

    }

    /**toString method of the Graph.
     * @return String of all the nodes and their adjacencyList*/
    public String printGraph(){
        //Implement this method
        //returns a String representation of the IoT graph in the adjacency list format: e.g.
        /*
            A: {(B,3), (D,2), (E,0.5)}
            B: {(A,3),(E,2)}
            C:{}
            D:{(A,2)}
            E:{(A,0.5),(B,2)}
        */
        String str = "";
        for (int i = 0; i < getNumNodes(); i++){

            str += nodesList.get(i).getIoTSensorNode().getName();
            str += ":{";
            LinkedList<Adjacent> adjacencyList = nodesList.get(i).getAdjacencyList();
            for (int j = 0; j < adjacencyList.size(); j++){
                str += "(";
                str += adjacencyList.get(j).getNeighbor().getName();
                str += ",";
                str += adjacencyList.get(j).getWeight();
                str += ")";

                if (j < adjacencyList.size() - 1){
                    str += ",";
                }
            }
            str += "}";
            if (i < getNumNodes() - 1){
                str += "\n";
            }

        }
        return str;
    }
}
