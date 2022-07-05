package setup;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static setup.SeleniumWebDriver.getDriver;

public class WaitHelper {

    private static final int TIMEOUT = 15;
    private static final int SLEEP = 200;

    public static void pageToBeLoaded() {
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(SLEEP)).until(pageLoadCondition);
        } catch (WebDriverException e) {
            e.printStackTrace();
            throw new WebDriverException("Page is not loaded");
        }
    }

    public static void elementToBeVisible(WebElement element) {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(SLEEP)).until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverException e) {
            e.printStackTrace();
            throw new WebDriverException("Element " + element.getTagName() + " is not visible");
        }
    }

    public static void elementToBeClickable(WebElement element) {
        elementToBeVisible(element);
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(SLEEP)).until(ExpectedConditions.elementToBeClickable(element));
        } catch (WebDriverException e) {
            e.printStackTrace();
            throw new WebDriverException("Element is not clickable");
        }
    }
}
