package pages;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import setup.BasePage;

import java.util.List;
import java.util.stream.Collectors;

import static setup.SeleniumWebDriver.getDriver;

public class HomePage extends BasePage<HomePage> {
    public final ImmutableList<String> navigationItems = ImmutableList.of("Business", "Benefits and possibilities",
            "Prices", "FAQ", "Support", "Blog");

    @FindBy(css = "nav li")
    private List<WebElement> navigationItemsWebElements;

    public HomePage() {
        super(getDriver());
    }

    @Override
    public String getPageURL() {
        return "/index";
    }

    @Override
    public HomePage open() {
        return this.openPage(HomePage.class);
    }

    @Override
    public HomePage init() {
        return this.initPage(HomePage.class);
    }

    public List<String> getNavigationItemsWebElementsTexts() {
        return navigationItemsWebElements.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
