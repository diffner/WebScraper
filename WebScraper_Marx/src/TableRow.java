import java.util.HashMap;

public class TableRow implements Comparable<TableRow> {
    public Job job;
    public int jobCount = 1;
    public HashMap<Asset, Integer> assets = new HashMap<>();

    public TableRow(Job job) {
        this.job = job;
    }

    @Override
    public int hashCode() {
        if (job != null)
            return this.job.hashCode();
        else return 0;
    }

    public void addAsset(String asset) {
        Asset tempAsset = new Asset(asset);
        if (assets.containsKey(asset)){
            assets.replace(tempAsset,assets.get(tempAsset)+1);

        } else {
            assets.put(tempAsset,1);
        }
    }

    @Override
    public int compareTo(TableRow that) {
        return this.hashCode()-that.hashCode();
    }
}
