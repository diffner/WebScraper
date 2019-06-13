public class TokenizedAdData {
    private String[] tokenizedJob;
    private String[] tokenizedAssets;

    public TokenizedAdData(){
        tokenizedJob = null;
        tokenizedAssets = null;
    }

    public TokenizedAdData(String[] jobTokens, String[] assetTokens) {
        tokenizedJob = jobTokens;
        tokenizedAssets = assetTokens;
    }

    public String[] getJob() {
        return tokenizedJob;
    }

    public void setJob(String[] title) {
        this.tokenizedJob = title;
    }

    public String[] getAssets() {
        return tokenizedAssets;
    }

    public void setAssets(String[] description) {
        this.tokenizedAssets = description;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Title: ");
        for (String s : tokenizedJob){
            builder.append(s + " ");
        }
        builder.append("\n");
        builder.append("Assets: ");
        for(String s : tokenizedAssets){
            builder.append(s + " ");
        }
        builder.append("\n\n");
        return builder.toString();
    }
}
