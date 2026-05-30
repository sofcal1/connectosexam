package io.cucumber.base;

import static io.cucumber.locators.LocatorElements.dynamicAText;
import static io.cucumber.locators.LocatorElements.dynamicDivAText;
import static io.cucumber.locators.LocatorElements.dynamicPText;
import static io.cucumber.locators.LocatorElements.dynamicTableTrTdTheadSpan;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.constants.CommonTypes;

public class Base {

    public static WebDriver browser;

    // logging helper
    public static void enterLog(String strLogs) {
        System.out.println(strLogs);
    }

    public static void setDefaultImplicitWait() {
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static WebElement waitForClickable(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static WebElement checkIfVisible(By locator, int seconds) {
        return new WebDriverWait(browser, Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static void goToURL(String strBaseURI) {
        enterLog("Going to '" + strBaseURI + "'");
        browser.get(strBaseURI);
        setDefaultImplicitWait();
    }

    // resolves xpath template with actual values
    public static By dynamicElement(By by, Object... values) {
        String xpath = by.toString().replace("By.xpath: ", "");
        return By.xpath(String.format(xpath, values));
    }

    // returns the locator based on element type
    public static By getElement(String strField, String strValue, String strType) {
        By tempElem = null;
        if (strType.equals(CommonTypes.ATEXT)) {
            tempElem = dynamicElement(dynamicAText, strValue);
        } else if (strType.equals(CommonTypes.PTEXT)) {
            tempElem = dynamicElement(dynamicPText, strValue);
        } else if (strType.equals(CommonTypes.DIV_ATEXT)) {
            tempElem = dynamicElement(dynamicDivAText, strField);
        }
        checkIfVisible(tempElem, 5);
        return tempElem;
    }

    public static By getElement(String strFieldOrValue, String strType) {
        return getElement(strFieldOrValue, strFieldOrValue, strType);
    }

    public static void clickElement(String strField, String strType) {
        By tempElem = getElement(strField, strType);
        enterLog("Clicking '" + strField + "' with element type '" + strType + "'");
        waitForClickable(tempElem, 5);
        browser.findElement(tempElem).click();
    }

    public static String getValue(String field, String strType) {
        By tempElem = getElement(field, strType);
        return browser.findElement(tempElem).getText();
    }

    public static void verifyElement(String strValue, String strType) {
        By tempElem = getElement(strValue, strType);
        checkIfVisible(tempElem, 5);
        boolean blnActual = browser.findElement(tempElem).isDisplayed();
        String message = blnActual
                ? "PASSED: '" + strValue + "' is displayed"
                : "FAILED: '" + strValue + "' visibility mismatch";
        enterLog(message);
        Assert.assertTrue(blnActual, message);
    }

    public static By getRowColumnValue(String strField, Integer iRow, String strValue, String strType) {
        By tempElem = null;
        if (strType.equals(CommonTypes.ROW_SPAN)) {
            tempElem = dynamicElement(dynamicTableTrTdTheadSpan, String.valueOf(iRow), strField, strValue);
        }
        enterLog("(Row: '" + iRow + "')(Column: '" + strField + "') has expected value: '" + strValue + "'");
        checkIfVisible(tempElem, 5);
        browser.findElement(tempElem);
        return tempElem;
    }

    public static void assertThat(ArrayList<String> strActual, String[] strExpected) {
        String actual = String.join(", ", strActual);
        String expected = String.join(", ", strExpected);
        String message = "FAILED: Values do not match\nActual   : " + actual + "\nExpected : " + expected;
        Assert.assertEquals(strActual.toArray(new String[0]), strExpected, message);
    }

    public static void quitBrowser() {
        browser.quit();
    }
}
