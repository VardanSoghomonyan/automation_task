package setup;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ANSIColors;

import java.io.File;
import java.io.IOException;

import static setup.SeleniumWebDriver.getDriver;
import static setup.SeleniumWebDriver.removeDriver;

public abstract class BaseTest {
    public Logger logger = Logger.getLogger(BaseTest.class);

    private static void takeErrorScreenShot(ITestResult result) {
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("errorScreenshots" + result.getName() + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void open(ITestResult result) {
        SeleniumWebDriver seleniumWebDriver = new SeleniumWebDriver();
        seleniumWebDriver.initDriver();
        BasicConfigurator.configure();

        logger.info("-----------------------------------------------------------------------------------");
        logger.info("------------------------------------Test Start-------------------------------------");
        logger.info("-----------------------------------------------------------------------------------");
        logger.info("--------------------- " + result.getMethod().getMethodName() + " --------------------");
        logger.info("-----------------------------------------------------------------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void close(ITestResult result) {

        //Logs for test status
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                logger.info(ANSIColors.ANSI_GREEN);
                logger.info(result.getMethod().getMethodName() + " TEST PASSED");
                logger.info(ANSIColors.ANSI_RESET);
                break;
            case ITestResult.FAILURE:
                logger.info(ANSIColors.ANSI_RED);
                logger.info(result.getMethod().getMethodName() + " TEST FAILED");
                logger.info("");
                logger.info("----------------Test fail cause-----------------");
                logger.info(result.getThrowable().getMessage());
                logger.info("------------------------------------------------");
                logger.info(ANSIColors.ANSI_RESET);
                takeErrorScreenShot(result);
                break;
            case ITestResult.SKIP:
                logger.info(ANSIColors.ANSI_YELLOW);
                logger.info(result.getMethod().getMethodName() + " TEST SKIPPED");
                logger.info("");
                logger.info("----------------Test skip cause-----------------");
                logger.info(result.getThrowable().getMessage());
                logger.info("------------------------------------------------");
                logger.info(ANSIColors.ANSI_RESET);
                break;
        }

        logger.info("-----------------------------------------------------------------------------------");
        logger.info("-------------------------------------Test End--------------------------------------");
        logger.info("-----------------------------------------------------------------------------------");
        logger.info("-----------------------------------------------------------------------------------");

        //Close driver after every test
        try {
            if (getDriver() != null) {
                getDriver().quit();
                removeDriver();
            }
        } catch (WebDriverException ignored) {
        }
    }
}
