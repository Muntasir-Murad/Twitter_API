package PageObjects;

import ReusableLibrary.AbstractClass;

public class BaseClass extends AbstractClass {

    public static HomePage Twitter_HomePage(){
        HomePage Twitter_HomePage = new HomePage(driver);
        return  Twitter_HomePage;
    }//end of method

    public static LoginPage Twitter_LoginPage(){
        LoginPage Twitter_LoginPage = new LoginPage(driver);
        return  Twitter_LoginPage;
    }//end of method

    public static ExplorePage Twitter_ExplorePage(){
        ExplorePage Twitter_ExplorePage = new ExplorePage(driver);
        return  Twitter_ExplorePage;
    }//end of method


}//end of java class