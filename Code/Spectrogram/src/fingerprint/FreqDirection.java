package fingerprint;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 8/3/13
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FreqDirection {
    Down(50), Even(100), Up(150), None(0);

    private int value;
    FreqDirection(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
