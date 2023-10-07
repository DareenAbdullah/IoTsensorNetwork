/**
 * IotSensorNode Class.
 */
public class IoTSensorNode {

    /**Private id variable.*/
    private int id;
    /**Private name variable.*/
    private String name;
    /**Private x coordinate variable.*/
    private double coordinateX;
    /**Private y coordinate variable.*/
    private double coordinateY;

    /**Constructor Method.
     * @param id of node
     * @param name of node
     * @param coordinateX of node
     * @param coordinateY of node*/
    public IoTSensorNode(int id,String name,double coordinateX,double coordinateY){
        this.id=id;
        this.name=name;
        this.coordinateX=coordinateX;
        this.coordinateY=coordinateY;  
    }
    
    /**Setter Method.
     * @param id of node*/
    public void setId(int id){
        this.id=id;
    }
    
    /**Setter Method.
     * @param name of node*/
    public void setName(String name){
        this.name=name;
    }
    
    /**Setter Method.
     * @param coordinateX of node*/
    public void setX_coordinate(double coordinateX){
        this.coordinateX=coordinateX;
    }

    /**Setter Method.
     * @param coordinateY of node*/
    public void setY_coordinate(double coordinateY){
        this.coordinateY=coordinateY;
    }
    
    /**Getter Method.
     * @return id*/
    public int getId(){
        return id;
    }
    
    /**Getter Method.
     * @return name*/
    public String getName(){
        return name;
    }

    /**Getter Method.
     * @return X coordinate*/
    public double getX_coordinate(){
        return coordinateX;
    }

    /**Getter Method.
     * @return Y coordinate*/
    public double getY_coordinate(){
        return coordinateY;
    }
}
