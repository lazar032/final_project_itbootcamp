package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AuthRoutesTests extends BasicTest{
    private WebDriver driver;
    private WebDriverWait wait;
    private BasicTest basicTest;
    private String login;
    private String home;
    private String profile;
    private String adminCities;
    private String adminUsers;

    @BeforeClass
    public void beforeClass() {
        basicTest = new BasicTest();
        basicTest.setup();
        driver = basicTest.driver;
        wait = basicTest.wait;
        login = "/login";
        home = "/home";
        profile = "/profile";
        adminCities = "/admin/cities";
        adminUsers = "/admin/users";
    }

    @Test(priority = 10)
    public void forbidVisitToHomeUrlIfNotAuthenticated() {
        driver.get(baseUrl + home );
        String actualResult = driver.getCurrentUrl();
        String expectedResult = login;
        Assert.assertTrue(actualResult.contains(expectedResult), "URL should contain '/login'");
    }
    @Test(priority =  20)
    public void forbidVisitToProfileUrlIfNotAuthenticated() {
        driver.get(baseUrl + profile);
        String actualResult = driver.getCurrentUrl();
        String expectedResult = login;
        Assert.assertTrue(actualResult.contains(expectedResult), "URL should contain '/login'");
    }

    @Test(priority = 30)
    public void forbidVisitToAdminCitiesUrlIfNotAuthenticated() {
        driver.get(baseUrl + adminCities);
        String actualResult = driver.getCurrentUrl();
        String expectedResult = login;
        Assert.assertTrue(actualResult.contains(expectedResult), "URL should contain '/login'");
    }
    @Test(priority = 40)
    public void forbitVisitToAdminUsersUrlIfNotAuthenticated() {
        driver.get(baseUrl + adminUsers);
        String actualResult = driver.getCurrentUrl();
        String expectedResult = login;
        Assert.assertTrue(actualResult.contains(expectedResult), "URL should contain '/login'");
    }
    @AfterMethod
    public void takeScreenShotIfTestFails(ITestResult result) {
        basicTest.takeScreenShotIfTestFails(result);
    }

    @AfterClass
    public void afterClass() {
        basicTest.afterClass();
    }

}