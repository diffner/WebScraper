import java.util.HashMap;

public class Table {
    private HashMap<TableRow, TableRow> table = new HashMap<TableRow, TableRow>();
    public int size = 0;


    /**
     * Använd den här.
     *
     * @param jobTokens
     * @param assetTokens
     */
    public void addJob(String[] jobTokens, String[] assetTokens) {
        Job job = new Job(jobTokens);
        TableRow temp = new TableRow(job);
        if (table.containsKey(temp)) {
            table.get(temp).jobCount++;
        } else {
            table.put(temp, temp);
            size++;
        }
        for (String asset : assetTokens) {
            AssetsSet.addAsset(asset, job);
            table.get(temp).addAsset(asset);
        }
    }

    public TableRow getRow(TableRow row) {
        return table.get(row);
    }
}
