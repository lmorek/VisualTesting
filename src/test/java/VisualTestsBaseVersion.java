import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VisualTestsBaseVersion {



    private static final String TEST_URL = "https://siili.com/";

    private WebDriver driver;

    @BeforeClass
    public void beforeTest() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(TEST_URL);

    }

    @Test
    public void shouldOpenPageAndCompare() {

        String storiesTab = ".//li/a[contains(text(),'Stories')]";

        driver.findElement(By.xpath(storiesTab)).click();

    }

    @AfterClass
    public void afterTest() {

        driver.quit();
    }
}