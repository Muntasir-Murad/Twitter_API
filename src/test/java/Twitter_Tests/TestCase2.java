package Twitter_Tests;

import PageObjects.BaseClass;
import ReusableLibrary.AbstractClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestCase2 extends AbstractClass {

    String consumerKey = "u02b3NCdqfCWTdF3w7nMPQkTc";
    String consumerSecret = "ecjN3bE9Nntg5TDHL3AeJdC6J0NDz7MtmL8PcT76O3egZ92feR";
    String accessToken = "722583567046459392-0H2G4Hg4BNPmK21NLW9FmqU3AfH4Fym";
    String tokenSecret = "engilhNbutJaNKad35tgrmnZ7cdYH3veJ6oM3nsYfzBeG";


    String tweetID;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up


    @Test()
    public void TwentyTweets() throws InterruptedException {
        String Tweet = null;
        for (int i = 0; i < 10; i++) {
            Tweet = "Auto tweet post #" + (i + 1);
            Response Resp = given()
                    .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                    .queryParam("status", Tweet)
                    .when().post("update.json")
                    .then().extract().response();
            if (Resp.statusCode() == 200){
                System.out.println("Status code is 200 and successful. Tweet number: " +i+ " has been generated.");
                logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number: " +i+ " has been generated.");
            } else {
                System.out.println("Status code is not successful: " + Resp.statusCode());
                logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
            }
        }//end of for loop

    }//End of twenty tweets test

    @Test(dependsOnMethods = "TwentyTweets")
    public void VerifyTweets() throws InterruptedException {


        driver.navigate().to("https://twitter.com/");
        Thread.sleep(30000);


        BaseClass.Twitter_HomePage().signInButton();
        Thread.sleep(2000);

        BaseClass.Twitter_LoginPage().userName("@Muntasir002");
        BaseClass.Twitter_LoginPage().userPassword("muntasir007");
        BaseClass.Twitter_LoginPage().loginButton();
        Thread.sleep(2000);

        driver.navigate().refresh();
        Thread.sleep(2000);

        Response Resp =
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                        .queryParam("screen_name", "@Muntasir002")
                        .when()
                        .get("user_timeline.json")
                        .then()
                        .extract()
                        .response();

        String getTweet = Resp.asString();
        JsonPath js = new JsonPath(getTweet);
        String FirstID = (js.get("id[0]")).toString();
        String SecondID = (js.get("id[1]")).toString();
        String FirstTweet = js.get("text[0]").toString();
        String SecondTweet = js.get("text[1]").toString();

        System.out.println("The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
        logger.log(LogStatus.INFO, "The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
        System.out.println("The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");
        logger.log(LogStatus.INFO, "The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");

    }

    @Test(dependsOnMethods = "VerifyTweets")
    public void DeleteTweets() throws InterruptedException {

        //Delete all those 20 tweets using for loop as separate @Test via API
        for (int i = 0; i < 5; i++) {

            Response Resp =
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                            .queryParam("screen_name", "@Muntasir002")
                            .when()
                            .get("user_timeline.json")
                            .then()
                            .extract()
                            .response();

            String getTweet = Resp.asString();
            JsonPath js = new JsonPath(getTweet);
            tweetID = (js.get("id[0]")).toString();
            // delete recent tweet through API

            given().
                    auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                    .queryParam("id", tweetID)
                    .when()
                    .post("destroy.json")
                    .then()
                    .extract()
                    .response();;

            if (Resp.statusCode() == 200){
                System.out.println("Status code is 200 and successful. Tweet number: " +i+ " has been deleted.");
                logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number: " +i+ " has been deleted.");
            } else {
                System.out.println("Status code is not successful: " + Resp.statusCode());
                logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
            }


        }

        //Verify Tweet has been deleted and sign off
        Response Resp2 = given()
                .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                .queryParam("screen_name", "@Muntasir002")
                .when()
                .get("user_timeline.json")
                .then()
                .extract()
                .response();

        String getTweet2 = Resp2.asString();
        JsonPath js2 = new JsonPath(getTweet2);
        String Tweet2 = js2.get("text[3]").toString();
        String Tweet1 = js2.get("text[4]").toString();

        if (Tweet1.equals("This is auto-generated tweet number: 1")){
            System.out.println("The first tweet has not been successfully deleted.");
            logger.log(LogStatus.FAIL, "The first tweet has not been successfully deleted.");
        } else {
            System.out.println("The first tweet has been successfully deleted from timeline.");
            logger.log(LogStatus.PASS, "The first tweet has been successfully deleted from timeline.");
        }
        if (Tweet2.equals("This is auto-generated tweet number: 2")){
            System.out.println("The second tweet has not been successfully deleted.");
            logger.log(LogStatus.FAIL, "The second tweet has not been successfully deleted.");
        } else {
            System.out.println("The second tweet has been successfully deleted from timeline.");
            logger.log(LogStatus.PASS, "The second tweet has been successfully deleted from timeline.");
        }

        driver.navigate().refresh();
        Thread.sleep(2000);
        BaseClass.Twitter_ExplorePage().option();
        BaseClass.Twitter_ExplorePage().signOut();
        BaseClass.Twitter_ExplorePage().ConfirmSignOut();




    }//End of verify and delete tweets test

}//End of Java Class
