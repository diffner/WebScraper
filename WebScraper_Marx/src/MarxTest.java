import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class MarxTest {

    private Table table = new Table();
    private String java = "java";
    private String utvecklare = "utvecklare";
    private String junior = "junior";
    private String[] jobTokens1 = new String[]{junior, java, utvecklare};
    private String[] jobTokens2 = new String[]{java, junior, utvecklare};
    private String[] jobTokens3 = new String[]{junior, utvecklare, java};
    private String[] assetTokens1 = new String[]{"java", "c", "python"};
    private String[] assetTokens2 = new String[]{"sql", "c", "haskell"};
    private String[] assetTokens3 = new String[]{"java", "haskell", "python"};


    @Test
    public void addTableRowWithUniqueJob() {
        // Act
        table.addJob(jobTokens1, assetTokens1);
        // Assert
        assertThat(table.size, equalTo(1));
    }

    @Test
    public void addTableRowWithExistingJob() {
        // Arrange
        table.addJob(jobTokens1, assetTokens1);
        int original = table.size;
        // Act
        table.addJob(jobTokens1, assetTokens2);
        // Assert
        assertThat(table.size, equalTo(original));
    }

    @Test
    public void createAJob() {
        // Arrange
        Job test;
        // Act
        test = new Job(jobTokens1);
        // Assert
        assertThat(test.toString(), equalTo("junior java utvecklare"));
    }

    @Test
    public void checkJobHashCodeUniqeness() {
        // Arrange
        Job job1 = new Job(jobTokens1);
        Job job2 = new Job(jobTokens2);
        Job job3 = new Job(jobTokens3);
        // Assert
        assertThat(job1.hashCode(), not(job2.hashCode()));
        assertThat(job2.hashCode(), not(job3.hashCode()));
        assertThat(job3.hashCode(), not(job1.hashCode()));
    }

    @Test
    public void checktableRowHashcode() {
        // Arrange
        TableRow row1 = new TableRow(new Job(jobTokens1));
        TableRow row2 = new TableRow(new Job(jobTokens1));
        TableRow row3 = new TableRow(new Job(jobTokens2));
        // Assert
        assertThat(row1.hashCode(), equalTo(row2.hashCode()));
        assertThat(row2.hashCode(), not(row3.hashCode()));
    }

}
