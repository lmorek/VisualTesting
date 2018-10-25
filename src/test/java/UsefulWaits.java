import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UsefulWaits {

    public static final int MAX_ANIMATION_LOAD_TIME = 5;

    private WebDriver driver;


    public UsefulWaits(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageURL(String correctURL, int timeOut, int pageLoadTimeOut) throws InterruptedException {
        System.out.println(String.format("Wait until the address of the page will contain %s", correctURL));
        try {
            while (!(driver.getCurrentUrl().contains(correctURL))) {
                if (timeOut > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Catch InterruptedException " + e.toString());
                    }
                    timeOut--;
                } else {
                    throw new TimeoutException();
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println(
                    String.format(
                            "Timeout Exception when waiting for the address of the page will contain %s", correctURL));
        }
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
    }

    public void waitForVisible(WebElement element, int seconds) {
        System.out.println("Waiting until element under " + Helpers.extractSelector(element) + " selector is visible");
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Unable to find element - waiting too long for: " + element);
        }
    }

    public void waitForVisible(By by, int seconds) {
        waitForVisible(driver.findElement(by), seconds);
    }

    public void waitForClickable(WebElement element, int seconds) {
        System.out.println("Waiting until element under " + Helpers.extractSelector(element) + " selector is clickable");
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("Unable to click on element - not visible or waiting too long for:" + element);
        }
    }

    public void waitForClickable(By by, int seconds) {
        System.out.println("Waiting until element under " + Helpers.extractSelector(driver.findElement(by)) + " selector is clickable");
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
        } catch (Exception e) {
            System.out.println("Unable to click on element - not visible or waiting too long for:" + by);
        }
    }

    public void waitUntilListChanges(List<WebElement> list, int seconds, int size) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);

        wait.until((ExpectedCondition<Boolean>) driver -> {
            int elementCount = list.size();
            return elementCount != size;
        });
    }
}
