package io.cucumber.glue;

import java.util.ArrayList;

import io.cucumber.base.Base;
import io.cucumber.constants.CommonTypes;
import io.cucumber.core.Context;
import io.cucumber.core.Manager;
import io.cucumber.java.en.Then;
import io.cucumber.utils.PropertyFile;

public class HomepageListsSteps extends Context {

    public HomepageListsSteps(Manager manager) {
        super(manager);
    }

    @Then("the homepage displays the expected list of examples")
    public void homepageLists () throws Exception {
        PropertyFile.getPropertyFile("homepagelists", "homepage-lists");
        ArrayList<String> arrActual = new ArrayList<>();
        String rawExpected = PropertyFile.getAttribute("expected.links");
        String[] strExpected = rawExpected.split(",");

        // loop through each link on the homepage by index
        for (int i = 1; i <= strExpected.length; i++) {
            String strValue = Base.getValue(String.valueOf(i), CommonTypes.DIV_ATEXT);
            arrActual.add(strValue);
        }

        Base.assertThat(arrActual, strExpected);
    }
}
