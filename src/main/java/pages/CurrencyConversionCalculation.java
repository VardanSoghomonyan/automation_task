package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import setup.BasePage;

import static setup.SeleniumWebDriver.getDriver;
import static setup.WaitHelper.elementToBeClickable;
import static setup.WaitHelper.elementToBeVisible;
import static utils.Util.getRandomNumber;

public class CurrencyConversionCalculation extends BasePage<CurrencyConversionCalculation> {

    @FindBy(css = "[data-ng-if='!currencyExchangeVM.loading']")
    private WebElement currencyExchangeRatesTable;

    @FindBy(css = "[data-ng-model='currencyExchangeVM.filter.from_amount']")
    private WebElement sellInput;

    @FindBy(css = "[data-ng-model='currencyExchangeVM.filter.to_amount']")
    private WebElement buyInput;

    public CurrencyConversionCalculation() {
        super(getDriver());
    }

    @Override
    public String getPageURL() {
        return "/fees/currency-conversion-calculator#/";
    }

    @Override
    public CurrencyConversionCalculation open() {
        return this.openPage(CurrencyConversionCalculation.class);
    }

    @Override
    public CurrencyConversionCalculation init() {
        return this.initPage(CurrencyConversionCalculation.class);
    }

    @Override
    public void isLoaded() {
        elementToBeVisible(currencyExchangeRatesTable);
    }

    public boolean isSellInputEmpty() {
        elementToBeVisible(sellInput);
        return sellInput.getAttribute("value").isEmpty();
    }

    public boolean isBuyInputEmpty() {
        elementToBeVisible(buyInput);
        return buyInput.getAttribute("value").isEmpty();
    }

    public void typeRandomNumberInSellInput(int minValue, int maxValue) {
        typeRandomNumberInInput(sellInput, minValue, maxValue);
    }

    public void typeRandomNumberInBuyInput(int minValue, int maxValue) {
        typeRandomNumberInInput(buyInput, minValue, maxValue);
    }

    private void typeRandomNumberInInput(WebElement webElement, int minValue, int maxValue) {
        if (minValue >= maxValue) {
            try {
                getDriver().quit();
                throw new RuntimeException("The minimum value should be smaller than the maximum value");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        elementToBeClickable(webElement);
        webElement.clear();
        webElement.sendKeys(Integer.toString(getRandomNumber(minValue, maxValue)));
    }
}
