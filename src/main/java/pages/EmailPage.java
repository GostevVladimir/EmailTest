package pages;

import exception.MessageException;
import helpers.TableHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailPage extends BasePage {

    TableHelper tableHelper;

    public EmailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@class = 'UI']")
    private WebElement table;

    @FindBy(xpath = "//div[@class='J-J5-Ji T-I T-I-JN Zd']")
    private WebElement alertAccept;

    public void openMessage(String messageAuthor, String messageSubject) throws MessageException {

        if (isElementPresent(By.xpath("//*[contains(text(), 'Google Meet теперь в Gmail')]"))) {
            alertAccept.click();
        }

        tableHelper = new TableHelper(table, driver);
        waitForElementPresent(table, driver);
        waitInvisibilityNewMessage(driver);
        tableHelper.getMessage(messageAuthor, messageSubject).click();
    }
}
