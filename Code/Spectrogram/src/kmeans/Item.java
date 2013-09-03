package kmeans;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/3/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    private int[] item;
    private String key;
    private double distance;

    public Item(int[] item, String key){
        this.item = item;
        this.key = key;
    }

    public int[] getItem() {
        return item;
    }

    public void setItem(int[] item) {
        this.item = item;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
