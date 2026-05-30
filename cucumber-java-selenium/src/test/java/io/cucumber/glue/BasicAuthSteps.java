package io.cucumber.glue;

import static io.cucumber.base.Base.*;
import io.cucumber.constants.CommonConstants;
import io.cucumber.constants.CommonTypes;
import io.cucumber.core.Context;
import io.cucumber.core.Manager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.utils.PropertyFile;

public class BasicAuthSteps extends Context {

    public BasicAuthSteps(Manager manager) {
        super(manager);
    }

    @When("the 'Basic Auth' example is opened")
    public void clickBasicAuth() {
    	// clicking this will trigger a browser popup so we handle auth via URL in the next step
        clickElement(CommonConstants.BASIC_AUTH, CommonTypes.ATEXT);
    }

    @When("valid credentials are supplied")
    public void enteringCredentials() throws Exception {
        PropertyFile.getPropertyFile("basicauth", "basic-auth");
        String username = PropertyFile.getAttribute("username");
        String password = PropertyFile.getAttribute("password");
        String uri = PropertyFile.getAttribute("uri");
        // bypass the auth popup by embedding credentials in the URL
        goToURL("https://" + username + ":" + password + uri);
    }

    @Then("Congratulations should be displayed")
    public void verifySuccessMessage() {
        verifyElement(CommonConstants.CONGRATULATIONS_PROPER_CREDENTIALS, CommonTypes.PTEXT);
    }
}
