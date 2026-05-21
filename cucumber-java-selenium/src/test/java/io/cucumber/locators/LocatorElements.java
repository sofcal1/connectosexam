package io.cucumber.locators;

import org.openqa.selenium.By;

public class LocatorElements {

    public static final By dynamicAText = By.xpath("//a[contains(text(), '%s')]");
    public static final By dynamicPText = By.xpath("//p[contains(text(), '%s')]");
    // matches a table cell by row number and column header name
    public static final By dynamicTableTrTdTheadSpan = By.xpath("//table[@id='table1']//tbody/tr[%s][td[position()=(count(//table[@id='table1']//thead//th[span[normalize-space(.)='%s']]/preceding-sibling::th)+1)][normalize-space(.)='%s']]");
    public static final By dynamicDivAText = By.xpath("//div[contains(@id,'content')]//ul//li[%s]//a");
}
