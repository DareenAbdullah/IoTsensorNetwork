//This class is complete. Don't modify it
/**Adjacent Class.
 * @author unknown*/
public class Adjacent {

    /**private IoTsensorNode neighbor variable.*/
    private IoTSensorNode neighbor;
    /**private weight of edge variable.*/
    private double weight; 

    /**Constructor.
     * @param neighbor IoTsensorNode
     * @param weight of the edge*/
    public Adjacent(IoTSensorNode neighbor, double weight){
        this.neighbor=neighbor;
        this.weight=weight;
    }

    /**Setter Method.
     * @param neighbor IoTSensorNode*/
    public void setNeighbor(IoTSensorNode neighbor){
        this.neighbor=neighbor; 
    }

    /**Setter Method.
     * @param weight of edge*/
    public void setWeight(double weight){
        this.weight=weight;
    }

    /**Getter Method.
     * @return IoTsensorNode neighbor*/
    public IoTSensorNode getNeighbor(){
        return neighbor; 
    }

    /**Getter Method.
     * @return weight of edge*/
    public double getWeight(){
        return weight;
    }  
} 
