package io.cucumber.base;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.constants.CommonTypes;
import io.cucumber.locators.LocatorElements;

public class Base {

    public static LocatorElements dynamicElem = new LocatorElements();
    public static WebDriver browser;

    public static void enterLog(String strLogs) {
        System.out.println(strLogs);
    }

    public static void setDefaultImplicitWait() {
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static void goToURL(String strBaseURI) {
        enterLog("Going to '" + strBaseURI + "'");
        browser.get(strBaseURI);
        setDefaultImplicitWait();
    }

    public static By dynamicElement(By by, Object... values) {
        String xpath = by.toString().replace("By.xpath: ", "");
        return By.xpath(String.format(xpath, values));
    }

    public static By getElement(String strField, String strValue, String strType) {
        By tempElem = null;
        if (strType.equals(CommonTypes.ATEXT)) {
            tempElem = dynamicElement(dynamicElem.dynamicAText, strValue);
        } else if (strType.equals(CommonTypes.PTEXT)) {
            tempElem = dynamicElement(dynamicElem.dynamicPText, strValue);
        } else if (strType.equals(CommonTypes.DIV_ATEXT)) {
            tempElem = dynamicElement(dynamicElem.dynamicDivAText, strField);
        }
        return tempElem;
    }

    public static By getElement(String strFieldOrValue, String strType) {
        return getElement(strFieldOrValue, strFieldOrValue, strType);
    }

    public static void clickElement(String strField, String strType) {
        By tempElem = getElement(strField, strType);
        setDefaultImplicitWait();
        enterLog("Clicking '" + strField + "' with element type '" + strType + "'");
        browser.findElement(tempElem).click();
    }

    public static String getValue(String field, String strType) {
        setDefaultImplicitWait();
        By tempElem = getElement(field, strType);
        return browser.findElement(tempElem).getText();
    }

    public static void verifyElement(String strValue, String strType) {
        By tempElem = getElement(strValue, strType);
        setDefaultImplicitWait();
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
            tempElem = dynamicElement(dynamicElem.dynamicTableTrTdTheadSpan, String.valueOf(iRow), strField, strValue);
        }
        enterLog("(Row: '" + iRow + "')(Column: '" + strField + "') has expected value: '" + strValue + "'");
        setDefaultImplicitWait();
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
