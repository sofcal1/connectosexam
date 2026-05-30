package io.cucumber.glue;

import java.util.ArrayList;

import static io.cucumber.base.Base.*;
import io.cucumber.constants.CommonConstants;
import io.cucumber.constants.CommonTypes;
import io.cucumber.core.Context;
import io.cucumber.core.Manager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.utils.PropertyFile;

public class DataTablesSteps extends Context {

    public DataTablesSteps(Manager manager) {
        super(manager);
    }

    @When("the 'Sortable Data Tables' example is opened")
    public void clickSortableData() {
        clickElement(CommonConstants.SORTABLE_DATA_TABLES, CommonTypes.ATEXT);
    }

    @Then("Example 1 displays the following results:")
    public void displayFollowingResults (DataTable dataTable) throws Exception {
        PropertyFile.getPropertyFile("datatables", "data-tables");

        ArrayList<String> arrFields = new ArrayList<>();
        arrFields.add(CommonConstants.LAST_NAME);
        arrFields.add(CommonConstants.FIRST_NAME);
        arrFields.add(CommonConstants.EMAIL);
        arrFields.add(CommonConstants.DUE);
        arrFields.add(CommonConstants.WEB_SITE);

        // validate each row and column against expected data
        for (int iRow = 1; iRow <= 4; iRow++) {
            String rawData = PropertyFile.getAttribute("testData" + iRow);
            String[] arrValues = rawData.split(",");
            for (int iCol = 0; iCol < arrFields.size(); iCol++) {
                String strField = arrFields.get(iCol);
                String strExpectedValue = arrValues[iCol];
                getRowColumnValue(strField, iRow, strExpectedValue, CommonTypes.ROW_SPAN);
            }
        }
    }
}
