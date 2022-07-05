package setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;

import static setup.Configurations.BROWSER;

public class SeleniumWebDriver {

    private static final ThreadLocal<WebDriver> thread = new ThreadLocal<>();

    public void initDriver() {
        switch (BROWSER.toUpperCase()) {
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                thread.set(new FirefoxDriver(firefoxOptions));
                break;
            case "SAFARI":
                WebDriverManager.safaridriver().setup();
                SafariOptions safariOptions = new SafariOptions();
                thread.set(new SafariDriver(safariOptions));
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                thread.set(new EdgeDriver(edgeOptions));
                break;
            case "CHROME":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOption = new ChromeOptions();
                thread.set(new ChromeDriver(chromeOption));
                break;
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return thread.get();
    }

    public static void removeDriver() {
        thread.remove();
    }
}
