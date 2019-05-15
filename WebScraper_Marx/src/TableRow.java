import java.util.HashMap;

public class TableRow {
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
    public boolean equals(Object that){

        if(that == null) return false;

        if (!TableRow.class.isAssignableFrom(that.getClass())) {
            return false;
        }

        final TableRow other = (TableRow) that;

        if(this.hashCode() == other.hashCode())
            return true;
        else return false;
    }
}
