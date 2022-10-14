package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class AdminCitiesTests {
    WebDriver driver;
    WebDriverWait wait;
    BasicTest basicTest;
    ITestResult result;

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
    public void visitTheAdminCitiesPageAndListCities() {
        basicTest.navPage.getLoginLink().click();
        basicTest.loginPage.getEmailInput().sendKeys("admin@admin.com");
        basicTest.loginPage.getPasswordInput().sendKeys("12345");
        basicTest.loginPage.getLoginButton().click();
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        String actualUrlResult = driver.getCurrentUrl();
        String expectedUrlResult = "/admin/cities";
        Assert.assertTrue(actualUrlResult.contains(expectedUrlResult), "URL route should be '/admin/cities'");

    }

    @Test(priority = 20)
    public void checkInputTypesForCreateEditNewCity() {
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        basicTest.citiesPage.getNewItemButton().click();
        basicTest.citiesPage.waitForEditNewDialog();
        String actualResult = basicTest.citiesPage.getNameInput().getAttribute("type");
        String expectedResult = "text";
        Assert.assertEquals(actualResult, expectedResult, "Input type should be 'text'");
    }

    @Test(priority = 30)
    public void createNewCity() {
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        basicTest.citiesPage.getNewItemButton().click();
        basicTest.citiesPage.waitForEditNewDialog();
        basicTest.citiesPage.getNameInput().sendKeys("Lazar Mitrovic's city");
        basicTest.citiesPage.getSaveButton().click();
        wait.until(webDriver -> basicTest.messagePopUpPage.successDialog().isDisplayed());
        String actualResult = basicTest.messagePopUpPage.getTextFromUserError();
        String expectedResult = "Saved successfully";
        Assert.assertTrue(actualResult.contains(expectedResult), "Popup should be 'Saved successfully'");

    }

    @Test(priority = 40)
    public void editCity() {
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        basicTest.citiesPage.getSearchInput().sendKeys("Lazar Mitrovic's city");
        basicTest.citiesPage.waitForNumberOfRows(1);
        basicTest.citiesPage.getRowEditButton(1).click();
        // Somehow not working with clear() method
        basicTest.citiesPage.getNameInput().sendKeys(Keys.CONTROL, "A");
        basicTest.citiesPage.getNameInput().sendKeys(Keys.BACK_SPACE);
        basicTest.citiesPage.getNameInput().sendKeys("Lazar Mitrovic's city Edited");
        basicTest.citiesPage.getSaveButton().click();
        wait.until(webDriver -> basicTest.messagePopUpPage.successDialog().isDisplayed());
        String actualResult = basicTest.messagePopUpPage.getTextFromUserError();
        String expectedResult = "Saved successfully";
        Assert.assertTrue(actualResult.contains(expectedResult), "Error should be 'Saved successfully'");
    }
    @Test(priority = 50)
    public void searchCity() {
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        basicTest.citiesPage.getSearchInput().sendKeys("Lazar Mitrovic's city Edited");
        basicTest.citiesPage.waitForNumberOfRows(1);
        String actualResult = basicTest.citiesPage.getTableCell(1, 2).getText();
        String expectedResult = basicTest.citiesPage.getSearchInput().getAttribute("value");
        Assert.assertEquals(actualResult, expectedResult, "Name value should be equal to search value");
    }
    @Test(priority = 60)
    public void deleteCity() {
        basicTest.navPage.getAdminButton().click();
        basicTest.navPage.getCitiesDropDownLink().click();
        basicTest.citiesPage.getSearchInput().sendKeys("Lazar Mitrovic's city Edited");
        basicTest.citiesPage.waitForNumberOfRows(1);
        String actualResult = basicTest.citiesPage.getTableCell(1, 2).getText();
        String expectedResult = basicTest.citiesPage.getSearchInput().getAttribute("value");
        Assert.assertEquals(actualResult, expectedResult, "Name value should be equal to search value");
        basicTest.citiesPage.getRowDeleteButton(1).click();
        basicTest.citiesPage.waitForDeleteDialog();
        basicTest.citiesPage.getDeleteButton().click();
        wait.until(webDriver -> basicTest.messagePopUpPage.successDialog().isDisplayed());
        String actualTextResult = basicTest.messagePopUpPage.getTextFromUserError();
        String expectedTextResult = "Deleted successfully";
        Assert.assertTrue(actualTextResult.contains(expectedTextResult), "Error should be 'Deleted successfully'");

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