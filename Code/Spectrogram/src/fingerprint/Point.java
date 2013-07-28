package fingerprint;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/27/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    private int start;
    private int end;
    private double[][] data;
    //Constructors
    public Point(){ }

    public Point(int start, int end, double[][] data){
        this.start = start;
        this.end = end;
        this.data = data;
    }
    public Point(int start, double[][] data){
        this.start = start;
        this.data = data;
    }
    //End Constructors

    //Getters and Setters
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    //End Getters and Setters
}
