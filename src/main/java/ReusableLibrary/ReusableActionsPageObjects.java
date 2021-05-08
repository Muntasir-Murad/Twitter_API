package ReusableLibrary;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class ReusableActionsPageObjects {

    //Create a reusable method to define the driver
    public static WebDriver defineTheDriver() throws IOException, InterruptedException {
        //Terminate previous instances of chrome driver
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
        //Wait time before running new instance
        Thread.sleep(1500);
        //Set the driver path
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        //Define the chromeoptions arguments
        ChromeOptions options = new ChromeOptions();
        //Maximize my driver
        options.addArguments("start-maximized");
        //Set the driver to incognito (private)
        options.addArguments("incognito");

        //define the WebDriver
        WebDriver driver = new ChromeDriver(options);

        return driver;
    }


    //Reusable Method to click on any webelement by explicit wait
    public static void clickOnElement(WebDriver driver,WebElement xpathLocator, ExtentTest logger, String elementName){
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver,10);
        //use try catch to locate an element and click on it
        try{
            wait.until(ExpectedConditions.visibilityOf(xpathLocator)).click();
            logger.log(LogStatus.PASS,"Successfully clicked on " + elementName);
        } catch (Exception e) {
            logger.log(LogStatus.FAIL,"Unable to click on element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            System.out.println("Unable to click on element " + elementName + " " + e);
        }
    }//End of clickOnElement Reusable Method


    //Reusable Method to submit on any webelement by explicit wait
    public static void submitOnElement(WebDriver driver, WebElement xpathLocator, ExtentTest logger, String elementName){
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //use try catch to locate an element and click on it
        try{
            wait.until(ExpectedConditions.visibilityOf(xpathLocator)).submit();
            logger.log(LogStatus.PASS, "Successfully submit on "+ elementName);
        } catch (Exception e){
            System.out.println("Unable to submit on element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            logger.log(LogStatus.FAIL, "Unable to submit on element " + elementName + " " + e);
        }
    }//End of submitOnElement Reusable Method


    //Reusable Method to type on any webelement by explicit wait
    public static void sendKeysMethod(WebDriver driver, WebElement xpathLocator, String userValue, ExtentTest logger, String elementName) {
        //define by explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //use try catch to locate an element and click on it
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpathLocator));
            element.clear();
            element.sendKeys(userValue);
        } catch (Exception e) {
            System.out.println("Unable to enter value on element " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            logger.log(LogStatus.FAIL, "Unable to enter value on element " + elementName + " " + e);
        }
    }//End of sendkeys Reusable Method


    //Reusable Method to select any webelement in a drop down by explicit wait
    public static void dropdownByText(WebDriver driver,WebElement xpath,String userValue,ExtentTest logger,String elementName){
        WebDriverWait wait = new WebDriverWait(driver,10);
        try{
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpath));
            Select dropDown = new Select(element);
            dropDown.selectByVisibleText(userValue);
            logger.log(LogStatus.PASS,"Successfully selected value " + userValue + " on " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to select a value from " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            logger.log(LogStatus.FAIL,"Unable to select a value from " + elementName + " " + e);
        }
    }//End of dropdown by text Reusable Method


    //Reusable Method to captureText of any webelement by explicit wait
    public static String captureText(WebDriver driver, WebElement xpath, int index, ExtentTest logger, String elementName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String result = "";
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfAllElements(xpath)).get(index);
            result = element.getText();
            logger.log(LogStatus.PASS, "Successfully captured a text on " + elementName);
        } catch (Exception e) {
            System.out.println("Unable to select a value from " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            logger.log(LogStatus.FAIL, "Unable to select a value from " + elementName + " " + e);
        }
        return result;
    }//End of capture text Reusable Method


    //Reusable Method to mouseHover of any webelement by explicit wait
    public static void mouseHover(WebDriver driver, WebElement xpath, ExtentTest logger, String elementName){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions action = new Actions(driver);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(xpath));
            action.moveToElement(element).perform();
            logger.log(LogStatus.PASS, "Successfully hover to " + elementName);
            Thread.sleep(1000);
        }catch (Exception e) {
            System.out.println("Unable to hover to " + elementName + " " + e);
            getScreenShot(driver,elementName,logger);
            logger.log(LogStatus.FAIL,"Unable to hover to an element "+ elementName + "" + e);
        }
    }//End of mouseHover Reusable Method


    //method to capture screenshot when logger fails
    public static void getScreenShot(WebDriver driver,String imageName, ExtentTest logger) {
        try {
            String fileName = imageName + ".png";
            String directory = null;
            String snPath = null;
            directory = "src/main/java/HTML_Report/Screenshots/";
            snPath = "Screenshots/";
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + fileName));
            //String imgPath = directory + fileName;
            String image = logger.addScreenCapture(snPath + fileName);
            logger.log(LogStatus.FAIL, "", image);
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Error occurred while taking SCREENSHOT!!!");
            e.printStackTrace();
        }
    }//end of getScreenshot method


}//end of class
