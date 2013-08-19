package fingerprint;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/27/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordRange {
    private int start;
    private int end;
    private int framesPerSecond;
    //Constructors
    public WordRange(){ }

    public WordRange(int start, int end,int framesPerSecond){
        this.start = start;
        this.end = end;
        this.framesPerSecond = framesPerSecond;
    }
    public WordRange(int start){
        this.start = start;
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

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    //End Getters and Setters
}
