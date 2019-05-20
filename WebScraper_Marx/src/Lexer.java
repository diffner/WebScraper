import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lexer {

    /**
     * Parses the rawData objects (the ads) to see if it matches any keywords
     * @param rawData
     * @return
     */
    public static ArrayList<TokenizedAdData> parse (ArrayList<RawAdData> rawData) {
        //Fetch Keywords
        String[] Keywords = getKeyWords();
        //Regenerate to List
        List<String> titleKeyWords = Arrays.asList(Keywords[0].split(" "));
        List<String> assetsKeyWords = Arrays.asList(Keywords[1].split(" "));
        ArrayList<TokenizedAdData> result = new ArrayList<>(rawData.size());
       //For each ad in rawData, se what match the keywords
        for (RawAdData ad : rawData) {
            TokenizedAdData tokenizedAdData = new TokenizedAdData();
            ArrayList<String> titleTokens = new ArrayList<>();
            ArrayList<String> assetTokens = new ArrayList<>();

            //Go through the title of the ad to se if it match titleKeywords
            String[] title = ad.getTitle().split(" ");
            //Arrays.stream(title).forEach(titleToken ->{if (titleKeyWords.contains(titleToken)){titleTokens.add(titleToken);}});
            for (String titleWord : title) {
                if (titleKeyWords.contains(titleWord)){
                    titleTokens.add(titleWord);
                }
            }
            //Go through the description to see if it matches assetsKeywords
            String[] description = ad.getDiscription().split(" ");
            //Arrays.stream(description).forEach(assetToken -> {if (assetsKeyWords.contains(assetToken)){assetTokens.add(assetToken);}});
            for (String descWord : description) {
                if (assetsKeyWords.contains(descWord)){
                    assetTokens.add(descWord);
                }
            }
            //Set tokens to TokenizedAdData-object
            if (titleTokens.size() > 0 && assetTokens.size() > 0){
                tokenizedAdData.setJob(titleTokens);
                tokenizedAdData.setAssets(assetTokens);
                result.add(tokenizedAdData);
            }
        }
        return result;
    }

    /**
     * Open the files with keywords, and return them as Strings
     * @return
     */
    private static String[] getKeyWords(){
        String titleKeyWordsTemp = "";
        try
        {
            titleKeyWordsTemp = new String ( Files.readAllBytes( Paths.get("src/titleKeyWords.txt"))).toLowerCase();
        }
        catch (Exception e) {
            System.out.println("Something went wrong when reading the titleKeyWords-file");
            System.exit(1);
        }
        String assetsKeyWordsTemp = "";
        try
        {
            assetsKeyWordsTemp = new String ( Files.readAllBytes( Paths.get("src/assetsKeyWords.txt"))).toLowerCase();
        }
        catch (Exception e) {
            System.out.println("Something went wrong when reading the assetsKeyWords-file");
            System.exit(1);
        }
        return new String[]{titleKeyWordsTemp, assetsKeyWordsTemp};
    }


}
