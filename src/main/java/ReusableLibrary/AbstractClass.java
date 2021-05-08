package ReusableLibrary;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class AbstractClass {

    public static WebDriver driver = null;
    public static ExtentReports reports = null;
    public static ExtentTest logger = null;


    //Before suit annotation
    @BeforeSuite
    public void setDriver() throws IOException, InterruptedException {
        driver = ReusableActions.defineTheDriver();

        //Set the path to the report I want to use
        reports = new ExtentReports("src/main/java/HTML_Report/Automation_Report.html");
    }//End of before suite

    @AfterSuite
    public void closeDriver() {
        //Flush the logs for the report
        reports.flush();
        //Quite the driver
        driver.quit();
    }//End of after suite


    //Before method will start the log for your report and capture the test name
    @BeforeMethod
    public void captureTestName(Method methodName){
        logger = reports.startTest(methodName.getName());
    }//End of before method

    //After method will end the logger(print statement for the report) for individual test
    @AfterMethod
    public void endLogger(){
        reports.endTest(logger);
    }//End of after method

}
