package fingerprint;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/29/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntensityPoint {
    private static final int SIMILRITY_BUFFER = 4;
    private int x;
    private int y;
    private double intensity;


    public IntensityPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntensityPoint(int x, int y, double intensity) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
    }

    public boolean isMatch(IntensityPoint point){
        boolean isMatch = false;
        if(point.getY() < y +SIMILRITY_BUFFER && point.getY() > y - SIMILRITY_BUFFER)
            isMatch = true;
        return isMatch;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}
