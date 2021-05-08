/*
UI
- navigate to twitter website
- Enter user name and password
- click on login
- click on tweet icon on left side in blue with +
- on pop up message enter your tweet
- click on the tweet button
- verify your current tweet is appearing on your dashboard//Done

API
- now using API verify the most recent tweet was created using if else logic with api
- capture the tweet id in API
- now delete the tweet via api

UI
- now refresh your ui page and verify the tweet is not present
- log out from twitter

 */
package Twitter_Tests;

import PageObjects.BaseClass;
import ReusableLibrary.AbstractClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class TestCase1 extends AbstractClass {
    String consumerKey = "u02b3NCdqfCWTdF3w7nMPQkTc";
    String consumerSecret = "ecjN3bE9Nntg5TDHL3AeJdC6J0NDz7MtmL8PcT76O3egZ92feR";
    String accessToken = "722583567046459392-0H2G4Hg4BNPmK21NLW9FmqU3AfH4Fym";
    String tokenSecret = "engilhNbutJaNKad35tgrmnZ7cdYH3veJ6oM3nsYfzBeG";



    String tweetID;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up


    @Test(priority = 1)
    public void TC1() throws InterruptedException, AWTException {

        //Post tweet through UI
        driver.navigate().to("https://twitter.com/");
        Thread.sleep(3000);


        BaseClass.Twitter_HomePage().signInButton();
        Thread.sleep(2000);

        BaseClass.Twitter_LoginPage().userName("@Muntasir002");
        BaseClass.Twitter_LoginPage().userPassword("muntasir007");
        BaseClass.Twitter_LoginPage().loginButton();
        Thread.sleep(2000);

        BaseClass.Twitter_ExplorePage().tweet();
        Thread.sleep(1000);
        BaseClass.Twitter_ExplorePage().message("API Testing");
        BaseClass.Twitter_ExplorePage().tweetButton();
        Thread.sleep(2000);

        String post= BaseClass.Twitter_ExplorePage().capturingtextmethod();



        Assert.assertEquals("API Testing",post);
        System.out.println(post);



        //Get recent tweet through API



        Response Resp=
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Muntasir002")
                        .when()
                        .get("user_timeline.json")
                        .then()
                        .extract()
                        .response();

        String getTweet=Resp.asString();
        JsonPath js=new JsonPath(getTweet);
        String tweetText= js.get("text[0]").toString();
        tweetID=(js.get("id[0]")).toString();


        if (tweetText.equals("Hey TestAPI")) {
            System.out.println("The following tweet has been created: "+ tweetText);
            System.out.println("The ID of recently created tweet is: "+ tweetID);
            logger.log(LogStatus.PASS, "The following tweet has been created: " + tweetText + "The ID of the tweet is: "+tweetID);
        } else {
            System.out.println("The following tweet has not been created: " + tweetText);
            System.out.println("ID is not available since tweet has not been posted.");
            logger.log(LogStatus.FAIL, "The following tweet has not been created: " + tweetText + " No ID available.");
        }


        //Delete recent tweet through API






    given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", tweetID)
                .when()
                .post("destroy.json")
                .then().extract().response();
        System.out.println("Recent twitter message with id '" + tweetID + "' has been deleted");
        logger.log(LogStatus.PASS,"Recent twitter message with id '" + tweetID + "' has been deleted");




        //Refresh page and verify tweet is deleted
        driver.navigate().refresh();
        Thread.sleep(2000);

        //Verify tweet is not present with API

        Response Resp1=
        given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("screen_name", "@Newaz86602618")
                .when()
                .get("user_timeline.json")
                .then()
                .extract()
                .response();

        String getNewTweet=Resp1.asString();
        JsonPath js1=new JsonPath(getNewTweet);
        String NewTweet= js1.get("text[0]");


        //String NewTweetID=(js1.get("id[0]"));


        Objects.toString("NewTweet", NewTweet);

        //Objects.toString("NewTweet", NewTweetID);

        if (NewTweet == null){
            System.out.println("The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
            logger.log(LogStatus.PASS, "The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
        }else {
            System.out.println("The recently posted tweet: " + tweetText + " is still present.");
            logger.log(LogStatus.INFO, "The recently posted tweet " + tweetText + " is still present.");

        }


        BaseClass.Twitter_ExplorePage().option();
        BaseClass.Twitter_ExplorePage().signOut();
        BaseClass.Twitter_ExplorePage().ConfirmSignOut();


    }//end of test
}//end of java class
