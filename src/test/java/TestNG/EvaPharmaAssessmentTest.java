package TestNG;

import ExtentReportsHelpers.ExtentListener;
import Pages.AdminPage;
import Pages.HomePage;
import Pages.LoginPage;
import Utilities.CustomSoftAssert;
import Utilities.EvaPharmaLogger;
import com.microsoft.playwright.*;
import jdk.jfr.Description;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.util.List;

import static Constants.Configurations.Configurations.webBrowser;
import static Constants.Configurations.Configurations.webUrl;

public class EvaPharmaAssessmentTest {
    Browser browser;
    BrowserContext context;
    Page page;
    ExtentListener extentListener;
    CustomSoftAssert softAssert;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    LoginPage loginPage ;
    HomePage homePage;
    AdminPage adminPage;
    int recordsBefore, recordsAfterAdd, recordsAfterDelete;

    @BeforeClass(groups = {"eva_assessment"})
    public void beforeClassSetup(){
        switch (webBrowser) {
            case "Chrome" ->
                    browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--start-maximized")));
            case "Edge" ->
                    browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false).setArgs(List.of("--start-maximized")));
            case "Firefox" ->
                    browser = Playwright.create().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(List.of("--width=" + width, "--height=" + height)));
        }
        extentListener = new ExtentListener("TestNG.EvaPharmaAssessmentTest");
        ThreadContext.put("Environment", System.getProperty("webUrl"));
        EvaPharmaLogger.evaPharmaLogger.info("Running Eva Pharma Assessment Automation Test");
        loginPage = new LoginPage();
        homePage = new HomePage();
        adminPage = new AdminPage();
    }

    @BeforeMethod(groups = {"eva_assessment"})
    public void beforeMethodSetup(){
        softAssert = new CustomSoftAssert();
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setIgnoreHTTPSErrors(true);
        if (webBrowser.equalsIgnoreCase("Firefox"))
            contextOptions.setViewportSize(width, height);
        else
            contextOptions.setViewportSize(null);
        context = browser.newContext(contextOptions);
        page = context.newPage();
        try {
            page.navigate(webUrl);
        }
        catch (PlaywrightException e) {
            EvaPharmaLogger.evaPharmaLogger.error("Error ", e);
            softAssert.assertTrue(false, "Navigation Error, Cannot Open Requested URL");
            softAssert.assertAll();
        }
    }

    @Test(groups = {"eva_assessment"})
    @Description("Check that number of records changes when adding and deleting admin users")
    public void manageAdminUsers(){
        try {
            loginPage.login(page, "Admin", "admin123");
            homePage.navigateToAdminPage(page);
            recordsBefore = adminPage.getNumberOfRecords(page);
            String employeeName = adminPage.getEmployeeName(page);
            adminPage.addNewAdmin(page, "automatedAdmin123", "P@ssw0rd", employeeName);
            recordsAfterAdd = adminPage.getNumberOfRecords(page);
            softAssert.assertEquals(recordsAfterAdd, recordsBefore + 1, "Number of records should be increased by 1");
            adminPage.searchForAdmin(page, "automatedAdmin123");
            adminPage.deleteAdmin(page);
            recordsAfterDelete = adminPage.getNumberOfRecords(page);
            softAssert.assertEquals(recordsAfterDelete, recordsAfterAdd - 1, "Number of records should be decreased by 1");
            softAssert.assertAll();
        }
        catch (AssertionError e){
            EvaPharmaLogger.evaPharmaLogger.error("Error ", e);
            softAssert.assertAll();
        }
        catch (PlaywrightException e) {
            EvaPharmaLogger.evaPharmaLogger.error("Error ", e);
            softAssert.assertTrue(false, "TEST CASE TERMINATED [web element was not found], Check log file.");
            softAssert.assertAll();
        }
    }


    @AfterMethod(alwaysRun = true, groups = {"eva_assessment"})
    public void afterMethodTearDown(ITestResult result){
        extentListener.onTestStart(result);
        if (result.getStatus() == 1) {
            extentListener.onTestSuccess();
        }
        else if (result.getStatus() == 2) {
            extentListener.onTestFailure(result, page, softAssert);
        }
        else if (result.getStatus() == 3) {
            extentListener.onTestSkipped(softAssert);
        }
        page.close();
        context.close();
    }

    @AfterClass(alwaysRun = true, groups = {"eva_assessment"})
    public void afterClassTearDown(){
        extentListener.onFinish();
        browser.close();
    }
}
