package Drivers;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.InstanceTools;
import net.sf.javaml.tools.data.FileHandler;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 8/26/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class KMEANSDRIVER {

    public static void main(String[] args){
        KMeans kmeans = new KMeans(7,100);
        Dataset dataset = new DefaultDataset();
        Instance instance = InstanceTools.randomInstance(35);
        instance.
        dataset.add()

    }
}
