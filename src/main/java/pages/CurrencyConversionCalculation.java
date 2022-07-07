package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import setup.BasePage;
import statics.CountriesEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static setup.SeleniumWebDriver.getDriver;
import static setup.WaitHelper.elementToBeClickable;
import static setup.WaitHelper.elementToBeVisible;
import static utils.Util.getRandomNumber;

public class CurrencyConversionCalculation extends BasePage<CurrencyConversionCalculation> {
    @FindBy(css = "[data-ng-model='currencyExchangeVM.filter.from_amount']")
    private WebElement sellInput;

    @FindBy(xpath = "(//span[@aria-label='Select box activate'])[1]")
    private WebElement sellCurrencyDropdown;

    @FindBy(css = "[data-ng-model='currencyExchangeVM.filter.to_amount']")
    private WebElement buyInput;

    @FindBy(xpath = "//tbody")
    private WebElement currencyExchangeRatesTable;

    @FindBy(xpath = "//th")
    private List<WebElement> conversionTableColumns;

    @FindBy(css = "tbody tr")
    private List<WebElement> conversionTableRows;

    @FindBy(xpath = "//footer//span[@role='button']")
    private WebElement flagIcon;

    @FindBy(css = "[id='countries-dropdown']")
    private WebElement countryDropdown;

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
        typeRandomNumberInExchangeInput(sellInput, minValue, maxValue);
    }

    public void typeRandomNumberInBuyInput(int minValue, int maxValue) {
        typeRandomNumberInExchangeInput(buyInput, minValue, maxValue);
    }

    private void typeRandomNumberInExchangeInput(WebElement webElement, int minValue, int maxValue) {
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

    public String getSellCurrency() {
        return sellCurrencyDropdown.getText();
    }

    public List<String> getCurrencyConversionTableColumns() {
        return conversionTableColumns.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private List<String> getCurrencyConversionTableRows() {
        return conversionTableRows.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getIthColumnCells(int ithColumn) {
        List<String> cells = new ArrayList<>();
        int columnsNumber = getCurrencyConversionTableColumns().size();
        int rowsNumber = getCurrencyConversionTableRows().size();
        if (ithColumn <= columnsNumber) {
            for (int i = 0; i < rowsNumber; i++) {
                cells.add(getDriver().findElement(By.xpath(String.format("(//td)[%d]", i * columnsNumber + ithColumn))).getText());
            }
            return cells;
        } else {
            getDriver().quit();
            try {
                throw new Exception("The provided column number is greater than the actual columns' number");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<String> getColumnCellsByColumnName(String columnName) {
        int columnNumber;
        List<String> columns = getCurrencyConversionTableColumns();
        if (columns.contains(columnName)) {
            columnNumber = columns.indexOf(columnName);
            return getIthColumnCells(columnNumber + 1);
        } else {
            getDriver().quit();
            try {
                throw new Exception("The provided column name does not exist");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean isCurrencyConversionTableEnlarged() {
        List<String> currencyConversionTableColumns = getCurrencyConversionTableColumns();
        return currencyConversionTableColumns.size() > currencyConversionTableColumns.indexOf("Paysera amount") + 1;
    }

    public void selectCountryThatHasEnlargedTable() {
        CountriesEnum[] countriesArray = CountriesEnum.values();
        for (CountriesEnum countriesEnum : countriesArray) {
            if (isCurrencyConversionTableEnlarged()) {
                break;
            } else {
                selectCountry(countriesEnum);
            }
        }
    }

    public void selectCountry(CountriesEnum countriesEnum) {
        elementToBeClickable(flagIcon);
        flagIcon.click();
        elementToBeClickable(countryDropdown);
        countryDropdown.click();
        WebElement country = getDriver().findElement(By.xpath(String
                .format("//ul[@aria-labelledby='countries-dropdown']//a[contains(@href,'%s')]", countriesEnum.getCountryISOCode())));
        elementToBeClickable(country);
        country.click();
        isLoaded();
    }
}
