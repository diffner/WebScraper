import java.util.HashMap;

public class Asset {
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
        else jobs.put(job,1);
    }

    @Override
    public int hashCode(){
        return type.hashCode();
    }

    @Override
    public boolean equals(Object that){

        if(that == null) return false;

        if (!Asset.class.isAssignableFrom(that.getClass())) {
            return false;
        }

        final Asset other = (Asset) that;

        if(this.hashCode() == other.hashCode())
            return true;
        else return false;
    }
}