package fingerprint;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/3/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public enum WordType {
    Screech(30),Note(60);

    private int value;
    WordType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
