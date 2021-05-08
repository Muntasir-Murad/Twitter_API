package PageObjects;

import ReusableLibrary.AbstractClass;
import ReusableLibrary.ReusableActionsPageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ExplorePage extends AbstractClass {

        ExtentTest logger;

        public ExplorePage(WebDriver driver) {
            PageFactory.initElements(driver, this);
            this.logger = AbstractClass.logger;
        }//end of constructor class

        //define all the WebElement on page
        @FindBy(xpath = "//a[@aria-label='Tweet']")
        WebElement tweet;
        @FindBy(xpath = "//div[@role='textbox']")
        WebElement message;
        @FindBy(xpath = "//div[@data-testid='tweetButton']")
        WebElement tweetButton;
        @FindBy(xpath = "//div[@aria-label='Account menu']")
        WebElement options;
        @FindBy(xpath = "//a[@data-testid='AccountSwitcher_Logout_Button']")
        WebElement signOut;
        @FindBy(xpath = "//div[@data-testid='confirmationSheetConfirm']")
        WebElement ConfirmSignOut;
        @FindBy(xpath ="//span[text()='API Testing']")
        WebElement CaptureText;



        //create a method for click
        public void tweet() {
            ReusableActionsPageObjects.clickOnElement(driver, tweet, logger, "tweet.");
        }//end of click method


        //create a method for sendkeys
        public void message(String userValue) {
            ReusableActionsPageObjects.sendKeysMethod(driver, message, userValue, logger, "message to be sent.");
        }//end of sendkeys method


        //create a method for click
        public void tweetButton() {
            ReusableActionsPageObjects.clickOnElement(driver, tweetButton, logger, "tweet.");
        }//end of click method


        //create a method for click
        public void option() {
            ReusableActionsPageObjects.clickOnElement(driver, options, logger, "user options.");
        }//end of click method


        //create a method for click
        public void signOut() {
            ReusableActionsPageObjects.clickOnElement(driver, signOut, logger, "log out.");
        }//end of click method

        //Beginning of click on confirm sign out method
        public void ConfirmSignOut(){
            ReusableActionsPageObjects.clickOnElement(driver, ConfirmSignOut, logger, "confirm log out");
        }



        public String capturingtextmethod(){

           String text= ReusableActionsPageObjects.captureText(driver,CaptureText,0,logger,"Capturing Text");

            return text;
        }

    }//end of java class
