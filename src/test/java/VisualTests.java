import com.applitools.eyes.exceptions.DiffsFoundException;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class VisualTests {

    private static final String TEST_URL = "https://siili.com/";
    private static final String API_KEY = "HERE_PUT_YOUR_API_KEY";
    private Eyes eyes = new Eyes();
    private WebDriver driver;

    @BeforeClass
    public void beforeTest() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(TEST_URL);

        eyes.setApiKey(API_KEY);
        eyes.open(driver, "ToDo", "VisualTest");
    }

    @Test
    public void shouldOpenPageAndCompare() {

        String storiesTab = ".//li/a[contains(text(),'Stories')]";

        driver.findElement(By.xpath(storiesTab)).click();
        eyes.checkWindow("Checking main page window");
    }

    @AfterClass
    public void afterTest() {
        try {
            eyes.close();
        } catch (DiffsFoundException e) {
            System.out.println("There are some differences in test comparision:" + e);
        } finally {
            eyes.abortIfNotClosed();
        }
        driver.quit();
    }
}
