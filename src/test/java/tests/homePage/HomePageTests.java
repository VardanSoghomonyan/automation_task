package tests.homePage;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import setup.BaseTest;

public class HomePageTests extends BaseTest {
    @Test
    public void checkHomePageNavigationItems() {
        HomePage homePage = new HomePage().open();
        Assert.assertEquals(homePage.getNavigationItemsWebElementsTexts(), homePage.navigationItems,
                "The navigation items on the Home page are incorrect");
    }
}
