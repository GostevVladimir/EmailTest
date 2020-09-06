package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "identifier")
    private WebElement emailInput;

    @FindBy(id = "identifierNext")
    private WebElement nextButton;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextButton;

    @FindBy(id = "headingText")
    private WebElement header;

    @FindBy(id = "//*[contains(text(),'Подтвердить')]")
    private WebElement acceptAccountButton;

    public void enterEmail(String email) {
        inputText(emailInput, email);
    }

    public void enterPassword(String password) {
        waitForElementPresent(passwordInput, driver);
        inputText(passwordInput, password);
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void clickPasswordNextButton() {
        passwordNextButton.click();
    }

    public String getHeaderText() {
        return header.getText();
    }

    public void login(String email, String password) {
        enterEmail(email);
        clickNextButton();
        enterPassword(password);
        clickPasswordNextButton();
        if (isElementPresent(By.xpath("//div[contains(text(),'Защитите свой аккаунт')]"))) {
            waitForElementPresent(acceptAccountButton, driver);
            acceptAccountButton.click();
        }
    }

    public void checkHeaderText(String text) {
        try {
            waitForElementPresent(header, driver);
            Assert.assertEquals(getHeaderText(), text);
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: " + alertText);
                alert.accept();
                waitForElementPresent(header, driver);
                Assert.assertEquals(getHeaderText(), text);
            } catch (NoAlertPresentException e) {
                e.printStackTrace();
            }
        }
    }
}
