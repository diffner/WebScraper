import java.util.HashMap;

public class AssetsSet {
    public static HashMap<Asset,Asset> assetsSet = new HashMap<Asset,Asset>();


    public static void addAsset(String asset, Job job) {
        Asset tempAsset = new Asset(asset, job);
        if (assetsSet.containsKey(tempAsset)){
            assetsSet.get(tempAsset).addJob(job);
        }else assetsSet.put(tempAsset,tempAsset);
    }
}
