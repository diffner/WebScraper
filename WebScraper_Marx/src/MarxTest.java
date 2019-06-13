import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class MarxTest {

    private String[] jobTokens1 = new String[]{"junior", "java", "utvecklare"};
    private String[] jobTokens2 = new String[]{"java", "junior", "utvecklare"};
    private String[] jobTokens3 = new String[]{"junior", "utvecklare", "java"};
    private String[] assetTokens1 = new String[]{"java", "c", "python"};
    private String[] assetTokens2 = new String[]{"sql", "c", "haskell"};
    private String[] assetTokens3 = new String[]{"java", "haskell", "python"};
    private TokenizedAdData tokenizedAdData1 = new TokenizedAdData();
    private TokenizedAdData tokenizedAdData2 = new TokenizedAdData();
    private TokenizedAdData tokenizedAdData3 = new TokenizedAdData();
    private DataStructureElement job1 = new DataStructureElement(jobTokens1,assetTokens1);
    private DataStructureElement job2 = new DataStructureElement(jobTokens1,assetTokens2);


    @Test
    public void testHashmapInJob(){
        // Arrange and Act
        DataStructureElement dataStructureElement = new DataStructureElement(jobTokens1,assetTokens1);
        dataStructureElement.addAssets(assetTokens2);
        // Assert
        assertThat(dataStructureElement.relations.get("c"), equalTo(2));
        assertThat(dataStructureElement.relations.get("java"), equalTo(1));
        assertThat(dataStructureElement.relations.get("sql"), equalTo(1));
    }

    @Test
    public void searchKeyInJob(){
        // Act
        Object[] keyset = job1.relations.keySet().toArray();
        // Assert
        assertThat(keyset.length, equalTo(3));
        assertThat(keyset[1], equalTo("java"));
        assertThat(keyset[2], equalTo("c"));
        assertThat(keyset[0], equalTo("python"));
    }

    @Test
    public void addJobToJobHashMap(){
        // Arrange
        HashMap<String[], DataStructureElement> hashMap = new HashMap<>();
        tokenizedAdData1.setJob(jobTokens1);
        tokenizedAdData1.setAssets(assetTokens1);
        tokenizedAdData2.setJob(jobTokens1);
        tokenizedAdData2.setAssets(assetTokens2);
        tokenizedAdData3.setJob(jobTokens2);
        tokenizedAdData3.setAssets(assetTokens2);
        //Act
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData1,hashMap);
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData2,hashMap);
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData3,hashMap);
        // Assert
        assertThat(hashMap.size(), equalTo(2));
        assertThat(hashMap.get(jobTokens1).relations.size(), equalTo(5));
        assertThat(hashMap.get(jobTokens2).relations.size(), equalTo(3));
        assertThat(hashMap.get(jobTokens1).relations.get("c"), equalTo(2));
        assertThat(hashMap.get(jobTokens1).relations.get("sql"), equalTo(1));
        assertThat(hashMap.get(jobTokens2).relations.get("c"), equalTo(1));
    }

    @Test
    public void addAssetToAssetHashmap(){
        // Arrange
        HashMap<String[],DataStructureElement> hashMap = new HashMap<>();
        tokenizedAdData1.setJob(jobTokens1);
        tokenizedAdData1.setAssets(assetTokens1);
        tokenizedAdData2.setJob(jobTokens1);
        tokenizedAdData2.setAssets(assetTokens2);
        tokenizedAdData3.setJob(jobTokens2);
        tokenizedAdData3.setAssets(assetTokens2);
        // Act
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData1,hashMap);
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData2,hashMap);
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData3,hashMap);
        // Assert
        assertThat(hashMap.size(),equalTo(5));
        assertThat(hashMap.get(new String[]{"c"}).relations.size(),equalTo(2));
        assertThat(hashMap.get(new String[]{"java"}).relations.size(),equalTo(1));
        assertThat(hashMap.get(new String[]{"haskell"}).relations.size(),equalTo(2));
        assertThat(hashMap.get(new String[]{"haskell"}).relations.get(Arrays.toString(jobTokens2)),equalTo(1));
        assertThat(hashMap.get(new String[]{"c"}).relations.get(Arrays.toString(jobTokens1)),equalTo(2));
        assertThat(hashMap.get(new String[]{"c"}).relations.get(Arrays.toString(jobTokens2)),equalTo(1));
    }
}
