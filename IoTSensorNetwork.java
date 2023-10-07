import java.util.*;
/**IoTSensorNetwork Class.
 * @author Dareen Abdullah*/
public class IoTSensorNetwork {
    
    /**Private variable networkGraph: IoTGraph Object.*/
    private IoTGraph networkGraph;
    /**ArrayList of the nodes that were visited.*/
    private ArrayList<IoTSensorNode> visited=new ArrayList<IoTSensorNode>(); //should contain the IoTSensorNodes already visited by the Forwarding protocol or else the protocol will run infinitely
    
    /**Default constructor.*/
    public IoTSensorNetwork(){
        //Default constructor creates an IoTSensorNetwork consisting of 500 nodes located in an area of 10x10
        IoTSensorNetwork sampleNetwork = new IoTSensorNetwork(500,10);
    }
    
    /**param constructor.
     * @param graph IoTGraph*/
    public IoTSensorNetwork(IoTGraph graph){
        networkGraph=new IoTGraph(graph.getNodesList());
        for (int i = 0; i<networkGraph.getNumNodes(); i++){
            IoTSensorNode node1=networkGraph.getNodesList().get(i).getIoTSensorNode();
            for (int j = 0; j<i; j++){
                IoTSensorNode node2=networkGraph.getNodesList().get(j).getIoTSensorNode();
                double ed=euclideanDistance(node1, node2);
                if (ed<=1){
                    networkGraph.addLink(node1,node2, ed);
                }
            }
        }
    }

    /**Parameterized Constructor.
     * @param numOfSensors the number of sensors
     * @param side area size*/
    public IoTSensorNetwork(int numOfSensors, double side){
        ArrayList<AdjacencyListHead> nodesList=new ArrayList<AdjacencyListHead>();
        for (int i = 0; i < numOfSensors; i++){
			double coordinateX = side*Math.random();  
			double coordinateY = side*Math.random();  
			int id=i;
            String name="node "+i; 
			IoTSensorNode node=new IoTSensorNode(id, name, coordinateX, coordinateY);
            nodesList.add(new AdjacencyListHead(node));              
		}
        networkGraph = new IoTGraph(nodesList); 
        //Iterate over all the sensor node pairs in the graph and connect the nodes with a distance <=1 (wireless range of sensors) with links
        //O(n^3)
        for (int i = 0; i < networkGraph.getNumNodes(); i++){
            for (int j = 0; j < networkGraph.getNumNodes(); j++){
                if (j != i){
                    IoTSensorNode node1 = networkGraph.nodeFromIndex(i);
                    IoTSensorNode node2 = networkGraph.nodeFromIndex(j);

                    IoTSensorNode test = new IoTSensorNode(node1.getId(), node1.getName(), node1.getX_coordinate(), node1.getY_coordinate());
                    double weight = euclideanDistance(node1, node2);
                    if (weight <= 1){
                        if (!edgeExists(node1, node2)){
                            //O(n) method
                            networkGraph.addLink(node1, node2, weight);
                        }
                    }
                }
            }
        }
    }

    /**Helper method: Checks if the edge is present.
     * @param node1 IoTSensorNode
     * @param node2 IoTSensorNode
     * @return boolean edgeExists*/
    private boolean edgeExists(IoTSensorNode node1,IoTSensorNode node2){
        //O(n)
        if(!nodeExists(node1) || !nodeExists(node2)){
            return false;
        }
        LinkedList<Adjacent> neighbors1 = networkGraph.getAdjacents(node1);
        for (int j = 0; j < neighbors1.size(); j++){
            if (neighbors1.get(j).getNeighbor().equals(node2)){
                return true;
            }
        }
        return false;
    }    
    
    /**Calculate the euclidean distance.
     * @param node1 IoTSensorNode
     * @param node2 IoTSensorNode
     * @return double distance*/
    double euclideanDistance(IoTSensorNode node1, IoTSensorNode node2){
        //Implement this method
        //returns the Euclidean distance between two nodes node1 and node2
        //O(1)
        return Math.sqrt(Math.pow((node2.getX_coordinate() - node1.getX_coordinate()), 2) 
            + Math.pow((node2.getY_coordinate() - node1.getY_coordinate()), 2) * 1.0);
    }
    
    /**Optimize the network.*/
    public void networkOptimizer(){	
        //BIG O IS O(N^3)

        //O(n*l*l) where n is the number of IoT sensor nodes and l is the number of links
        //Implement this method
        /*for each node x in the graph
            for each node y adjacent to x
                for each node z adjacent to y 
        */
        // If |xz|<|xy| and |yz|<|xy|, delete link |xy| from the graph
        boolean delete;
        ArrayList<IoTSensorNode[]> edgesToRemove = new ArrayList<IoTSensorNode[]>();
        
        //O(n^3)
        for (int x = 0; x < networkGraph.getNumNodes(); x++){
            IoTSensorNode nodeX = networkGraph.nodeFromIndex(x);
            //O(n) method
            LinkedList<Adjacent> adjacencyListX = networkGraph.getAdjacents(nodeX);
            //This entire for loop is O(3n) or O(n)
            for (int y = 0; y < adjacencyListX.size(); y++){
                delete = false;
                IoTSensorNode nodeY = adjacencyListX.get(y).getNeighbor();
                //O(n) method
                LinkedList<Adjacent> adjacencyListY = networkGraph.getAdjacents(nodeY);
                //O(n)
                for (int z = 0; z < adjacencyListY.size(); z++){
                    delete = false;
                    IoTSensorNode nodeZ = adjacencyListY.get(z).getNeighbor();
                    if (nodeZ.equals(nodeX)){
                        continue;
                    }
                    double distanceXZ = euclideanDistance(nodeX, nodeZ);
                    double distanceXY = euclideanDistance(nodeX, nodeY);
                    double distanceYZ = euclideanDistance(nodeY, nodeZ);
                    //Both are a shorter distance then |XY|
                    if ((distanceXZ < distanceXY) && (distanceYZ < distanceXY)){
                        //delete the link
                        delete = true;
                        break;
                    }
                }
                if (delete){
                    IoTSensorNode[] nodes = new IoTSensorNode[2];
                    nodes[0] = nodeX;
                    nodes[1] = nodeY;
                    edgesToRemove.add(nodes);
                }
            }
        }
        
        //O(n^2)
        for (int i = 0; i < edgesToRemove.size(); i++){
            IoTSensorNode node1 = edgesToRemove.get(i)[0];
            IoTSensorNode node2 = edgesToRemove.get(i)[1];
            //O(n) method
            if (edgeExists(node1, node2)){
                //O(n) method
                networkGraph.removeLink(node1, node2);
            }
        }
	}

    /**Getter method for the graph.
     * @return IoTGraph object*/
    public IoTGraph getIoTGraph(){
        return networkGraph;
    }

    /**Method to verify a visited node.
     * @param node IoTSensorNode
     * @param visited ArrayList of nodes
     * @return boolean isVisited*/
    protected boolean isVisited(IoTSensorNode node, ArrayList<IoTSensorNode> visited){
        if(visited.contains(node)){
            return true;
        }
        else{
            return false;
        }
	}
    
    /**Find the fowarding path from a sourceNode to a destNode.
     * @param sourceNode starting node
     * @param destNode destination node
     * @return arrayList the fowarding path*/
    public ArrayList<IoTSensorNode> three10ForwardingProtocol(IoTSensorNode sourceNode,IoTSensorNode destNode){
        //Implement this method
        //O(n*l) where n is the number of IoT sensor nodes and l is the number of links
        ArrayList<IoTSensorNode> path = new ArrayList<IoTSensorNode>();
        //Check if sourceNode and destNode exist in the graph and that they are not null. If this is not the case, raise an IllegalArgumentException
        if (sourceNode == null || destNode == null){
            throw new IllegalArgumentException();
        }
        //O(n) method
        if (!nodeExists(sourceNode) || !nodeExists(destNode)){
            throw new IllegalArgumentException();
        }
        //Clearing visited before I start my recursions, just to be safe iykyk
        visited.clear();
        //Check if sourceNode equals destNode. If yes, clear the visited ArrayList and return a 1-element path consisting of the sourceNode.
        if (sourceNode.equals(destNode)){
            visited.clear();
            path.add(sourceNode);
            return path;
        }
        //Else:
        //Check if current node on the path doesn't have adjacent neighbors. if yes, clear the visited ArrayList and return an empty path
        //O(n) method
        LinkedList<Adjacent> sourceNeighbors = networkGraph.getAdjacents(sourceNode);
        if (sourceNeighbors.size() == 0){
            visited.clear();
            //return path or an empty arraylist?
            path.clear();
            return path;
        }
        //Else if:
        // The current node has only one adjacent node which belongs to the visited ArrayList
        // This indicates that there is no way to move forward from that node since its only neighbor is already visited
        // In this case return an empty path
        if (sourceNeighbors.size() == 1){
            if (isVisited(sourceNeighbors.get(0).getNeighbor(), visited)){
                visited.clear();
                path.clear();
                return path;
            }
        }
        // Else:
        //Find the neighbor of the current node with the smallest angle (maximum cosine) with the vector connecting the current node with the destination node
        //mark the current node visited and add the neighbor to the path
        visited.add(sourceNode);
        return recurHelper(sourceNode, destNode);

    }

    /**Helper method: Recursively loops until the destination node is reached.
     * @param sourceNode current node.
     * @param destNode the destination node.
     * @return the fowarding path*/
    private ArrayList<IoTSensorNode> recurHelper(IoTSensorNode sourceNode,IoTSensorNode destNode){
        ArrayList<IoTSensorNode> path = new ArrayList<IoTSensorNode>();
        LinkedList<Adjacent> sourceNeighbors = networkGraph.getAdjacents(sourceNode);
        if (sourceNode.equals(destNode)){
            visited.clear();
            path.add(sourceNode);
            return path;
        }
        //Find the neighbor of the current node with the smallest angle (maximum cosine) with the vector connecting the current node with the destination node
        //mark the current node visited and add the neighbor to the path
        double max = 0;
        //Check if all the neighbors were visited
        boolean firstVisit = true;
        int maxIndex = -1;
        for (int i = 0; i < sourceNeighbors.size(); i++){
            IoTSensorNode nextHop = sourceNeighbors.get(i).getNeighbor();
            //O(n) function
            if (!isVisited(nextHop, visited)){
                double angle = cos(sourceNode, destNode, nextHop);
                if (firstVisit){
                    max = angle;
                    maxIndex = i;
                    firstVisit = false;
                }
                else if (angle > max){
                    max = angle;
                    maxIndex = i;
                }
            }
        }
        //if firstVisit is still true then all neighboring nodes have been visited
        //return an empty path
        if (firstVisit){  
            path.clear();
            return path;
        }
        IoTSensorNode nextHop = sourceNeighbors.get(maxIndex).getNeighbor();
        visited.add(nextHop);
        path = recurHelper(nextHop, destNode);
        //If the path return was empty then call the function with the same node to try a different node
        //The next best max angle that is not visited
        if (path.size() == 0){
            return recurHelper(sourceNode, destNode);
        }
        path.add(sourceNode);
        return path;

    }

    /**Helper method: nodeExists.
     * @param node IoTSensorNode
     * @return boolean nodeExists*/
    private boolean nodeExists(IoTSensorNode node){
        //BIG O COMPLEXITY IS O(N) 
        int index = 0;
        int count = 0;

        for (int i = 0; i < networkGraph.getNumNodes(); i++){
            if (networkGraph.getNodesList().get(i).getIoTSensorNode().equals(node)){
                index = i;
                count += 1;
                break;
            }
        }
        if (count == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**Angle Calculater.
    * @param node current node
    * @param destination destNode
    * @param nextHop node
    * @return double angle*/
    private double cos(IoTSensorNode node, IoTSensorNode destination, IoTSensorNode nextHop){       
        //method code complete. Don't modify
        //returns the trigonometric cosine of the angle between nextHop and the vector connecting node to destination
        double hyp = euclideanDistance(destination, nextHop);
		double side1 = euclideanDistance(destination, node);
		double side2 = euclideanDistance(node, nextHop);
		return (side1*side1 + side2*side2 -hyp*hyp)/(2*side1*side2);        
	}
}