import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Helpers extends DesiredCapabilities {
    private static WebDriver driver;
    private static WebDriverWait driverWait;

    /**
     * Initialize the webdriver. Must be called before using any helper methods. *
     */
    public static void init(WebDriver webDriver) {
        driver = webDriver;
        //serverAddress = driverServerAddress;
        int timeoutInSeconds = 30;
        driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
    }

    /**
     * Set implicit wait in seconds. *
     */
    public static void setWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Return an element by locator. *
     */
    public static WebElement element(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Return a list of elements by locator. *
     */
    public static List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Press the back button. *
     */
    public static void back() {
        driver.navigate().back();
    }

    /**
     * Return a list of elements by tag name. *
     */
    public static List<WebElement> tags(String tagName) {
        return elements(forTags(tagName));
    }

    /**
     * Return a tag name locator. *
     */
    public static By forTags(String tagName) {
        return By.className(tagName);
    }

    /**
     * Return a static text element by xpath index. *
     */
    public static WebElement searchByText(int xpathIndex) {
        return element(forText(xpathIndex));
    }

    /**
     * Return a static text locator by xpath index. *
     */
    public static By forText(int xpathIndex) {
        return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
    }

    /**
     * Return a static text locator that contains text. *
     */
    public static By forText(String text) {
        return By.xpath("//android.widget.TextView[contains(@text, '" + text + "')]");
    }

    /**
     * Return a static text element that contains text. *
     */
    public static WebElement text(String text) {
        return element(forText(text));
    }

    /**
     * Return a static text element by exact text. *
     */
    public static WebElement textExact(String text) {
        return element(forTextExact(text));
    }

    /**
     * Return a static text locator by exact text. *
     */
    public static By forTextExact(String text) {
        return By.xpath("//android.widget.TextView[@text='" + text + "']");
    }

    /**
     * Return selector to very completed path.
     * "//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
     * "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
     * "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
     * "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]"
     *
     * @param value Some String.
     * @return By selector.
     */
    public static By for_find(String value) {
        return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value
                + "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value
                + "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value
                + "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]");
    }

    /**
     * Return a WebElement by given locator.
     */
    public static WebElement find(By locator) {
        return wait(locator);
    }

    /**
     * Return a list of all WebElements by given locator.
     */
    public static List<WebElement> findAll(By locator) {
        return elements(locator);
    }

    /**
     * Wait 30 seconds for locator to find an element. *
     */
    public static WebElement wait(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait 30 seconds for locator to find all elements. *
     */
    public static List<WebElement> waitAll(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait 30 seconds for locator to not find a visible element. *
     */
    public static boolean waitInvisible(By locator) {
        return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    //TODO implement for Appium driver
    //    /**
    //     * Return an element that contains name or text. *
    //     */
    //    public static WebElement scroll_to(String value) {
    //        return driver.scrollTo(value);
    //    }
    //
    //    /**
    //     * Return an element that exactly matches name or text *
    //     */
    //    public static WebElement scroll_to_exact(String value) {
    //        return driver.scrollToExact(value);
    //    }

    /**
     * Change driver's window tab handle.
     */
    public static String getOtherWindowHandle() {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(driver.getWindowHandle())) {
                return handle;
            }
        }
        throw new NoSuchWindowException("There is only one window available.");
    }

    public static String extractSelector(WebElement element) {
        String el = element.toString();
        return el.substring(el.indexOf(">") + 1);
    }
}
