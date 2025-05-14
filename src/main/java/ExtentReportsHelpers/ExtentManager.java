package ExtentReportsHelpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Paths;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);

        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Tester", "Eva User");
        extent.setSystemInfo("Organization", "Eva Pharma");
        return extent;
    }

    public static String screenshotName;
    public static byte[] captureScreenshot(ITestResult result, Page page, String date) {
        String path = "./Screenshots/Failed " + date.substring(0, 10) + "/" + date.substring(11, 19);
        new File(path).mkdir();
        screenshotName = path + "/" + result.getName() + " test screenshot.png";
        page.waitForTimeout(1000);
        return page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotName)));
    }
}
