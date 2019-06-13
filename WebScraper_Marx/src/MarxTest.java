import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class MarxTest {

    private String[] jobTokens1 = new String[]{"junior", "java", "utvecklare"};
    private String[] jobTokens2 = new String[]{"java", "junior", "utvecklare"};
    private String[] jobTokens3 = new String[]{"junior", "utvecklare", "java"};
    private String[] jobTokens4 = new String[]{"junior", "java", "utvecklare"};
    private String[] assetTitle1 = new String[]{"c"};
    private String[] assetTitle2 = new String[]{"java"};
    private String[] assetTitle3 = new String[]{"haskell"};
    private String[] assetTokens1 = new String[]{"java", "c", "python"};
    private String[] assetTokens2 = new String[]{"sql", "c", "haskell"};
    private String[] assetTokens3 = new String[]{"java", "haskell", "python"};
    private TokenizedAdData tokenizedAdData1 = new TokenizedAdData(jobTokens1,assetTokens1);
    private TokenizedAdData tokenizedAdData2 = new TokenizedAdData(jobTokens4,assetTokens2);
    private TokenizedAdData tokenizedAdData3 = new TokenizedAdData(jobTokens2,assetTokens3);
    private DataStructureElement job1 = new DataStructureElement(jobTokens1,assetTokens1);
    private DataStructureElement job2 = new DataStructureElement(jobTokens1,assetTokens2);
    private DataStructureElement job3 = new DataStructureElement(jobTokens1,assetTokens1);
    private DataStructureElement asset1 = new DataStructureElement(assetTitle1,Arrays.toString(jobTokens1));
    private DataStructureElement asset2 = new DataStructureElement(assetTitle2,Arrays.toString(jobTokens2));
    private DataStructureElement asset3 = new DataStructureElement(assetTitle3,Arrays.toString(jobTokens3));


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
        HashMap<DataStructureElement, DataStructureElement> hashMap = new HashMap<>();
        //Act
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData1,hashMap);
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData2,hashMap);
        hashMap = WebScraperMarx.generateJobHashmap(tokenizedAdData3,hashMap);
        // Assert
        assertThat(hashMap.size(), equalTo(2));
        assertThat(hashMap.get(job1).relations.size(), equalTo(5));
        assertThat(hashMap.get(job2).relations.size(), equalTo(5));
        assertThat(hashMap.get(job1).relations.get("c"), equalTo(2));
        assertThat(hashMap.get(job1).relations.get("sql"), equalTo(1));
        assertThat(hashMap.get(job2).relations.get("c"), equalTo(2));
    }

    @Test
    public void addAssetToAssetHashmap(){
        // Arrange
        HashMap<DataStructureElement,DataStructureElement> hashMap = new HashMap<>();
        // Act
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData1,hashMap);
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData2,hashMap);
        hashMap = WebScraperMarx.generateAssetHashmap(tokenizedAdData3,hashMap);
        // Assert
        assertThat(hashMap.size(),equalTo(5));
        assertThat(hashMap.get(asset1).relations.size(),equalTo(1));
        assertThat(hashMap.get(asset2).relations.size(),equalTo(2));
        assertThat(hashMap.get(asset3).relations.size(),equalTo(2));
        assertThat(hashMap.get(asset3).relations.get(Arrays.toString(jobTokens2)),equalTo(1));
        assertThat(hashMap.get(asset1).relations.get(Arrays.toString(jobTokens1)),equalTo(2));
        assertThat(hashMap.get(asset1).relations.get(Arrays.toString(jobTokens2)),equalTo(null));
    }
}
