package tests.currencyConversionCalculation;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CurrencyConversionCalculation;
import setup.BaseTest;

import static setup.SeleniumWebDriver.getDriver;

public class CurrencyConversionCalculationTests extends BaseTest {
    @Test
    public void checkCurrencyConversionCalculationSellAndBuyInputs() throws Exception {
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
}
