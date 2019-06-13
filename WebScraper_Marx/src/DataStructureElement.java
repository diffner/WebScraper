import java.util.Arrays;
import java.util.HashMap;
import java.util.SplittableRandom;

public class DataStructureElement {
    public String[] title;
    HashMap<String, Integer> relations = new HashMap<>();


    public DataStructureElement(String[] jobTokens, String[] assetTokens) {
        title = jobTokens;
        for (String s : assetTokens) {
            relations.put(s,1);
        }
    }

    public DataStructureElement(String[] assetToken, String jobToken) {
        title = assetToken;
        relations.put(jobToken,1);
    }

    public void addAssets(String[] assetTokens) {
        for (String s : assetTokens) {
            Integer value = relations.get(s);
            if (value == null)
                relations.put(s, 1);
            else
                relations.put(s, ++value);
        }
    }

    public void addJob(String jobTitle) {
        Integer value = relations.get(jobTitle);
        if (value == null){
            relations.put(jobTitle, 1);
        }
        else {
            relations.put(jobTitle, ++value);
        }
    }

    @Override
    public String toString() {
        String result = "";
        for( String s : this.title){
            result += s + " ";
        }
        result = result.substring(0, result.length()-2);
        return result;
    }

    @Override
    public int hashCode() {
        int accumulator = 0;
        for(String s : this.title){
            accumulator += s.hashCode();
            accumulator = accumulator % 10000019;
        }
        return accumulator;
    }

    @Override
    public boolean equals(Object that) {

        if (that == null) return false;

        if (!DataStructureElement.class.isAssignableFrom(that.getClass())) {
            return false;
        }

        final DataStructureElement other = (DataStructureElement) that;

        if (this.hashCode() == other.hashCode())
            return true;
        else return false;
    }
}
