import java.util.Arrays;

public class Job implements Comparable<Job> {
    public String text;
    public Job right;

    public Job(String[] jobTokens) {
        text = jobTokens[0];
        if (jobTokens.length > 1)
            right= new Job(Arrays.copyOfRange(jobTokens,1,jobTokens.length));
    }

    @Override
    public String toString(){
        if (right != null)
            return text + " " + right.toString();
        else return text;
    }

    @Override
    public int hashCode(){
        int hash = this.text.hashCode()*32;
        if(right != null)
            hash = (hash % right.hashCode())*72;
        return hash;
    }

    @Override
    public int compareTo(Job that) {
        return this.hashCode()-that.hashCode();
    }
}
