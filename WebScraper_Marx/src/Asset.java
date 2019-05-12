import java.util.HashMap;

public class Asset implements Comparable<Asset> {
    public String type;
    public HashMap<Job, Integer> jobs = new HashMap<>();

    public Asset(String token, Job job){
        type= token;
        addJob(job);
    }

    public Asset(String asset) {
        type = asset;
    }

    public void addJob(Job job) {
        if (jobs.containsKey(job))
            jobs.replace(job,jobs.get(job)+1);

    }

    @Override
    public int hashCode(){
        return type.hashCode();
    }

    @Override
    public int compareTo(Asset that) {
        return this.hashCode()-that.hashCode();
    }
}