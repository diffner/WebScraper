import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;


public class Lexer {

    /**
     * Parses the rawData objects (the ads) to see if it matches any keywords
     * @param rawData
     * @return
     */
    public static ArrayList<TokenizedAdData> parse (ArrayList<RawAdData> rawData) {
        //Fetch Keywords, first index is string with title keywords, second is string with assets keywords
        String[] Keywords = getKeyWords();
        //Regenerate to List
        List<String> titleKeyWords = Arrays.asList(Keywords[0].split("\\s"));
        List<String> assetsKeyWords = Arrays.asList(Keywords[1].split("\\s"));
        ArrayList<TokenizedAdData> result = new ArrayList<>(rawData.size());
       //For each ad in rawData, se what match the keywords
        for (RawAdData ad : rawData) {
            TokenizedAdData tokenizedAdData = new TokenizedAdData();
            ArrayList<String> titleTokens = new ArrayList<>();
            ArrayList<String> assetTokens = new ArrayList<>();

            //Make a pattern from each titleKeyWord and see if it matches something in the title of the ad.
            String[] title = ad.getTitle().split("[^A-Öa-ö0-9#]+");
            //System.out.println(Arrays.toString(title));
            for (String titleKeyWord : titleKeyWords) {
                String regex = titleKeyWord;
                Pattern pattern = Pattern.compile(regex);
                //TODO: When implementing a better NLP, change this for-loop to a simpler, similar to the one when seaching for asset-words.
                for (int i = 0; i < title.length; i++) {
                    String temp = title[i];
                    Matcher matcher = pattern.matcher(temp);
                    //Special case for "deep-learning" and "machine-learning"
                    if (matcher.matches() && (titleKeyWord.equals("deep")||titleKeyWord.equals("machine"))){
                        System.out.println("deep");
                        if (title[i+1].equals("learning")){
                            titleTokens.add(titleKeyWord);
                            i++;
                        }
                    }
                    else if (matcher.matches()) {
                        titleTokens.add(titleKeyWord);
                    }
                }
            }
            //Make a pattern from each assetKeyWord and see if it matches something in the ad.
            String[] description = ad.getDiscription().split("[^A-Öa-ö0-9#]+");
            //System.out.println(" " + Arrays.toString(description) + "\n");
            for (String assetKeyWord : assetsKeyWords){
                String regex = assetKeyWord;
                Pattern pattern = Pattern.compile(regex);
                for (String descWord : description) {
                    Matcher matcher = pattern.matcher(descWord);
                    if (matcher.matches()) {
                        assetTokens.add(descWord);
                    }
                }
            }
            //Set tokens to TokenizedAdData-object
          
            if (titleTokens.size() > 0 && assetTokens.size() > 0){
                tokenizedAdData.setJob(titleTokens);
                tokenizedAdData.setAssets(assetTokens);
                result.add(tokenizedAdData);
            }

        }
        //System.out.println("");
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
