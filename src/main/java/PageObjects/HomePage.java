package PageObjects;

import ReusableLibrary.AbstractClass;
import ReusableLibrary.ReusableActionsPageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractClass {

    ExtentTest logger;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = AbstractClass.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//a[@data-testid='loginButton']")
    WebElement signInButton;



    //create a method for click
    public void signInButton() {
        ReusableActionsPageObjects.clickOnElement(driver, signInButton, logger, "Log in.");
    }//end of click method

}
