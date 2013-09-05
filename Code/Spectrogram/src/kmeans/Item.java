package kmeans;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/3/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Item<T extends Number>{
    protected T[] item;
    protected String key;
    protected double distance;

    public Item(T[] item, String key){
        this.item = item;
        this.key = key;
    }

    public T[] getItem() {
        return item;
    }

    public void setItem(T[] item) {
        this.item = item;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
