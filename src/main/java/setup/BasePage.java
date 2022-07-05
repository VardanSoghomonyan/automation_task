package setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static setup.WaitHelper.pageToBeLoaded;

public abstract class BasePage<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    private final String BASE_URL = "https://www.paysera.com/v2/en-GB";
    protected WebDriver driver;

    protected abstract String getPageURL();

    public abstract T open();

    public abstract T init();

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected T openPage(Class<T> _class) {
        T page = PageFactory.initElements(driver, _class);
        load();
        return page.get();
    }

    protected T initPage(Class<T> _class) {
        T page = PageFactory.initElements(driver, _class);
        return page.get();
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + getPageURL());
    }

    @Override
    protected void isLoaded() throws Error {
        pageToBeLoaded();
    }
}
