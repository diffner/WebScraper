import java.util.ArrayList;

public class TokenizedAdData {
    private ArrayList<String> TokenizedJob;
    private ArrayList<String> TokenizedAssets;

    public ArrayList<String> getJob() {
        return TokenizedJob;
    }

    public void setJob(ArrayList<String> title) {
        this.TokenizedJob = title;
    }

    public ArrayList<String> getAssets() {
        return TokenizedAssets;
    }

    public void setAssets(ArrayList<String> description) {
        this.TokenizedAssets = description;
    }
}
