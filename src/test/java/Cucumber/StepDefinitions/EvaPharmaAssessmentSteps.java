package Cucumber.StepDefinitions;

import Pages.AdminPage;
import Pages.HomePage;
import Pages.LoginPage;
import Utilities.CustomSoftAssert;
import com.microsoft.playwright.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import java.util.List;

import static Constants.Configurations.Configurations.webBrowser;
import static Constants.Configurations.Configurations.webUrl;

public class EvaPharmaAssessmentSteps {
    Browser browser;
    BrowserContext context;
    Page page;
    CustomSoftAssert softAssert;
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    AdminPage adminPage = new AdminPage();

    int recordsBefore;
    int recordsAfterAdd;
    int recordsAfterDelete;

    @Before
    public void beforeScenario() {
        switch (webBrowser) {
            case "Chrome":
                browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
            case "Edge":
                browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("msedge")
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
            case "Firefox":
                browser = Playwright.create().firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                break;
        }

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setIgnoreHTTPSErrors(true);
        context = browser.newContext(contextOptions);
        page = context.newPage();

        page.setViewportSize(1920, 1080);
        page.navigate(webUrl);
        softAssert = new CustomSoftAssert();
    }

    @Given("I log in as an Admin with username {string} and password {string}")
    public void iLoginAsAdmin(String username, String password) {
        loginPage.login(page, username, password);
    }

    @Given("I navigate to the Admin page")
    public void iNavigateToAdminPage() {
        homePage.navigateToAdminPage(page);
    }

    @When("I get the current number of admin records")
    public void iGetTheCurrentNumberOfAdminRecords() {
        recordsBefore = adminPage.getNumberOfRecords(page);
    }

    @When("I add a new admin user with username {string} and password {string}")
    public void iAddANewAdminUser(String username, String password) {
        String employeeName = adminPage.getEmployeeName(page);
        adminPage.addNewAdmin(page, username, password, employeeName);
    }

    @Then("the number of records should increase by {int}")
    public void theNumberOfRecordsShouldIncreaseBy(int increment) {
        recordsAfterAdd = adminPage.getNumberOfRecords(page);
        softAssert.assertEquals(recordsAfterAdd, recordsBefore + increment, "Number of records should be increased by 1");
    }

    @When("I search for the admin user {string}")
    public void iSearchForTheAdminUser(String username) {
        adminPage.searchForAdmin(page, username);
    }

    @When("I delete the new admin user")
    public void iDeleteTheNewAdminUser() {
        adminPage.deleteAdmin(page);
    }

    @Then("the number of records should decrease by {int}")
    public void theNumberOfRecordsShouldDecreaseBy(int decrement) {
        recordsAfterDelete = adminPage.getNumberOfRecords(page);
        softAssert.assertEquals(recordsAfterDelete, recordsAfterAdd - decrement, "Number of records should be decreased by 1");
        softAssert.assertAll();
    }

    @After
    public void afterScenario() {
        page.close();
        context.close();
        browser.close();
    }
}
