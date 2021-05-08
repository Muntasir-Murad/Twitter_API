package PageObjects;

import ReusableLibrary.AbstractClass;
import ReusableLibrary.ReusableActionsPageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractClass {

    ExtentTest logger;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = AbstractClass.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//input[@name='session[username_or_email]']")
    WebElement userName;
    @FindBy(xpath = "//input[@name='session[password]']")
    WebElement userPassword;
    @FindBy(xpath = "//div[@data-testid='LoginForm_Login_Button']")
    WebElement loginButton;


    //create a method for sendkeys
    public void userName(String userValue) {
        ReusableActionsPageObjects.sendKeysMethod(driver, userName, userValue, logger, "username");
    }//end of sendkeys method


    //create a method for sendkeys
    public void userPassword(String userValue) {
        ReusableActionsPageObjects.sendKeysMethod(driver, userPassword, userValue, logger, "password");
    }//end of sendkeys method


    //create a method for click
    public void loginButton() {
        ReusableActionsPageObjects.clickOnElement(driver, loginButton, logger, "login button");
    }//end of click method



}
