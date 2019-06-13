import java.util.ArrayList;

public class TokenizedAdData {
    private String[] TokenizedJob;
    private String[] TokenizedAssets;

    public String[] getJob() {
        return TokenizedJob;
    }

    public void setJob(String[] title) {
        this.TokenizedJob = title;
    }

    public String[] getAssets() {
        return TokenizedAssets;
    }

    public void setAssets(String[] description) {
        this.TokenizedAssets = description;
    }
}
