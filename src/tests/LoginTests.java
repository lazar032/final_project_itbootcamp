package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class LoginTests extends BasicTest {
    WebDriver driver;
    WebDriverWait wait;
    BasicTest basicTest;

    @BeforeClass
    public void setup() {
        basicTest = new BasicTest();
        basicTest.setup();
        driver = basicTest.driver;
        wait = basicTest.wait;
    }

    @BeforeMethod
    public void beforeMethod() {
        basicTest.beforeMethod();
    }

    @Test(priority = 10)
    public void visitTheLoginPage() {

        basicTest.navPage.getLanguageButton().click();
        basicTest.navPage.getEnglishLanguage().click();
        basicTest.navPage.getLoginLink().click();
        String actualResult = driver.getCurrentUrl();
        String expectedResult = "/login";
        Assert.assertTrue(actualResult.contains(expectedResult));

    }

    @Test(priority = 20)
    public void checkInputTypes() {
        basicTest.navPage.getLoginLink().click();
        String actualEmailResult = basicTest.loginPage.getEmailInput().getAttribute("type");
        String expectedEmailResult = "email";
        Assert.assertEquals(actualEmailResult, expectedEmailResult, "Attribute 'type' should be 'email'");
        String actualPasswordResult = basicTest.loginPage.getPasswordInput().getAttribute("type");
        String expectedPasswordResult = "password";
        Assert.assertEquals(actualPasswordResult, expectedPasswordResult, "Attribute 'type' should be 'password'");
    }

    @Test(priority = 30)
    public void displayErrorWhenUserDoesNotExist() {
        basicTest.navPage.getLoginLink().click();
        basicTest.loginPage.getEmailInput().sendKeys("non-existing-user@gmal.com");
        basicTest.loginPage.getPasswordInput().sendKeys("password123");
        basicTest.loginPage.getLoginButton().click();
        basicTest.messagePopUpPage.waitForUserError();
        String actualResult = basicTest.messagePopUpPage.getLoginErrorElement().getText();
        String expectedResult = "User does not exists";
        Assert.assertTrue(actualResult.contains(expectedResult), "User should exist");

    }

    @Test(priority = 40)
    public void displayErrorWhenPasswordIsWrong() {
        basicTest.navPage.getLoginLink().click();
        basicTest.loginPage.getEmailInput().sendKeys("admin@admin.com");
        basicTest.loginPage.getPasswordInput().sendKeys("password123");
        basicTest.loginPage.getLoginButton().click();
        basicTest.messagePopUpPage.waitForUserError();
        String actualResult = basicTest.messagePopUpPage.getLoginErrorElement().getText();
        String expectedResult = "Wrong password";
        Assert.assertTrue(actualResult.contains(expectedResult), "Wrong password");
    }

    @Test(priority = 50)
    public void validLogin() throws InterruptedException {
        basicTest.navPage.getLoginLink().click();
        basicTest.loginPage.getEmailInput().sendKeys("admin@admin.com");
        basicTest.loginPage.getPasswordInput().sendKeys("12345");
        basicTest.loginPage.getLoginButton().click();
        wait.until(ExpectedConditions.urlContains("home"));
        String actualResult = driver.getCurrentUrl();
        String expectedResult = "/home";

        Assert.assertTrue(actualResult.contains(expectedResult), "URL should contain '/home'");
    }

    @Test(priority = 60)
    public void logout() {

        Assert.assertTrue(basicTest.navPage.getLogoutButton().isDisplayed());
        basicTest.navPage.getLogoutButton().click();
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