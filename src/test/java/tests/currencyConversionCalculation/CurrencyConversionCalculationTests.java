package tests.currencyConversionCalculation;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CurrencyConversionCalculation;
import setup.BaseTest;
import statics.CountriesEnum;
import utils.Util;

import java.util.List;

import static setup.SeleniumWebDriver.getDriver;

public class CurrencyConversionCalculationTests extends BaseTest {
    @Test
    public void currencyConversionCalculationSellAndBuyInputsTests() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        CurrencyConversionCalculation currencyConversionCalculation = new CurrencyConversionCalculation().open();

        boolean isSellInputEmpty = currencyConversionCalculation.isSellInputEmpty();
        boolean isBuyInputEmpty = currencyConversionCalculation.isBuyInputEmpty();

        //check that initially only one of the inputs are filled
        Assert.assertTrue(isSellInputEmpty || isBuyInputEmpty, "Initially Sell and Buy inputs are filled");
        Assert.assertFalse(isSellInputEmpty && isBuyInputEmpty, "Initially Sell and Buy inputs are empty");

        for (int i = 0; i < 2; i++) {
            if (isSellInputEmpty) {
                currencyConversionCalculation.typeRandomNumberInSellInput(100, 200);
                isSellInputEmpty = currencyConversionCalculation.isSellInputEmpty();
                isBuyInputEmpty = currencyConversionCalculation.isBuyInputEmpty();

                softAssert.assertTrue(isSellInputEmpty || isBuyInputEmpty,
                        "The Buy input remained filled after filling the Sale input");
                softAssert.assertFalse(isSellInputEmpty && isBuyInputEmpty,
                        "After filling the Sale input the both inputs were empty");
            } else if (isBuyInputEmpty) {
                currencyConversionCalculation.typeRandomNumberInBuyInput(100, 200);
                isSellInputEmpty = currencyConversionCalculation.isSellInputEmpty();
                isBuyInputEmpty = currencyConversionCalculation.isBuyInputEmpty();

                softAssert.assertTrue(isSellInputEmpty || isBuyInputEmpty,
                        "The Sale input remained filled after filling the Buy input");
                softAssert.assertFalse(isSellInputEmpty && isBuyInputEmpty,
                        "After filling the Buy input the both inputs were empty");
            } else {
                getDriver().quit();
                throw new Exception("Sell and Buy inputs remained filled after filling both of them");
            }
        }
        softAssert.assertAll();
    }

    @Test
    public void countryChangeTest() {
        CountriesEnum country = CountriesEnum.POLAND;
        SoftAssert softAssert = new SoftAssert();
        CurrencyConversionCalculation currencyConversionCalculation = new CurrencyConversionCalculation().open();

        String initiallySetCurrency = currencyConversionCalculation.getSellCurrency();
        List<String> initiallyAvailablePayseraAmounts = currencyConversionCalculation.getColumnCellsByColumnName("Paysera amount");

        currencyConversionCalculation.selectCountry(country);
        String newCurrency = currencyConversionCalculation.getSellCurrency();

        softAssert.assertFalse(initiallySetCurrency.equals(newCurrency), "The currency was not changed");
        softAssert.assertNotEquals(currencyConversionCalculation.getColumnCellsByColumnName("Paysera amount"),
                initiallyAvailablePayseraAmounts, "The rates were not updated");
        softAssert.assertEquals(newCurrency, Util.getCurrencyCode(country),
                "The currency does not match to the selected country");
        softAssert.assertAll();
    }

    @Test
    public void conversionLossTest() {
        SoftAssert softAssert = new SoftAssert();
        CurrencyConversionCalculation currencyConversionCalculation = new CurrencyConversionCalculation().open();
        currencyConversionCalculation.selectCountryThatHasEnlargedTable();

        List<String> countryNames = currencyConversionCalculation.getColumnCellsByColumnName("Currency");
        List<String> initiallyAvailablePayseraAmounts = currencyConversionCalculation.getColumnCellsByColumnName("Paysera amount");
        List<String> bankProvidersAmounts = currencyConversionCalculation.getIthColumnCells(5);

        for (int i = 0; i < initiallyAvailablePayseraAmounts.size(); i++) {
            String bankProvidersAmount = bankProvidersAmounts.get(i);

            if (Character.isDigit(bankProvidersAmount.charAt(0))) {
                double payseraAmount = Double.parseDouble(initiallyAvailablePayseraAmounts.get(i).replace(",", ""));

                String[] rateAndLoss = bankProvidersAmount.split("\n");
                double rate = Double.parseDouble(rateAndLoss[0].replace(",", ""));
                if (rateAndLoss.length == 1) {
                    softAssert.assertEquals(payseraAmount, rate, "Paysera and bank amounts are not equal on "
                            + countryNames.get(i) + " row, but they should be");
                } else {
                    double loss = Double.parseDouble(rateAndLoss[1].replace("(", "").replace(")", "").replace(",", ""));
                    softAssert.assertEquals(payseraAmount + loss, rate, "Calculated Paysera and bank amounts " +
                            "difference is incorrect on " + countryNames.get(i) + " row");
                }
            }
        }
        softAssert.assertAll();
    }
}

