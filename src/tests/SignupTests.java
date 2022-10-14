package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class SignupTests {
    BasicTest basicTest;
    WebDriver driver;
    WebDriverWait wait;
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
    public void visitTheSignupPage() {
        basicTest.navPage.getSignUpButton().click();
        String actualResult = driver.getCurrentUrl();
        String expectedResult = "/signup";
        Assert.assertTrue(actualResult.contains(expectedResult), "URL route should be '/signup'");
    }

    @Test(priority = 20)
    public void checkInputTypes() {
        basicTest.navPage.getSignUpButton().click();
        String actualEmailResult = basicTest.signupPage.getEmailInput().getAttribute("type");
        String actualPasswordResult = basicTest.signupPage.getPasswordInput().getAttribute("type");
        String expectedEmailResult = "email";
        String expectedPasswordResult = "password";
        Assert.assertEquals(actualEmailResult, expectedEmailResult, "Attribute 'type' should be 'email'");
        Assert.assertEquals(actualPasswordResult, expectedPasswordResult, "Attribute 'type' should be 'password'");
    }
    @Test(priority = 30)
    public void displayErrorWhenUserAlreadyExist() {
        basicTest.navPage.getSignUpButton().click();
        String actualUrlResult = driver.getCurrentUrl();
        String expectedUrlResult = "/signup";
        Assert.assertTrue(actualUrlResult.contains(expectedUrlResult), "URL route should be '/signup'");
        basicTest.signupPage.getNameInput().sendKeys("Another User");
        basicTest.signupPage.getEmailInput().sendKeys("admin@admin.com");
        basicTest.signupPage.getPasswordInput().sendKeys("12345");
        basicTest.signupPage.getConfirmPasswordInput().sendKeys("12345");
        basicTest.signupPage.getSignupButton().click();
        basicTest.messagePopUpPage.waitForUserError();
        String actualErrorResult = basicTest.messagePopUpPage.getLoginErrorElement().getText();
        String expectedErrorResult = "E-mail already exists";
        Assert.assertEquals(actualErrorResult, expectedErrorResult, "Error should be 'E-mail already exists'");
        String actualUrlAfterResult = driver.getCurrentUrl();
        String expectedUrlAfterResult = "/signup";
        Assert.assertTrue(actualUrlAfterResult.contains(expectedUrlAfterResult), "URL route should be '/signup'");
    }
    @Test(priority = 40)
    public void signup() {
        basicTest.navPage.getSignUpButton().click();
        basicTest.signupPage.getNameInput().sendKeys("Ilija Nestorovic");
        basicTest.signupPage.getEmailInput().sendKeys("ilija.nestorovic@itbootcamp.rs");
        basicTest.signupPage.getPasswordInput().sendKeys("12345");
        basicTest.signupPage.getConfirmPasswordInput().sendKeys("12345");
        basicTest.signupPage.getSignupButton().click();
        basicTest.navPage.getHomeLink().click();
        basicTest.messagePopUpPage.waitForVerifyError();
        String actualResult = basicTest.messagePopUpPage.getTextFromVerifyError();
        String expectedResult = "IMPORTANT: Verify your account";
        Assert.assertTrue(actualResult.contains(expectedResult), "Dialog should contain 'IMPORTANT: Verify your account'");
        basicTest.messagePopUpPage.getVerifyCloseButton().click();
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