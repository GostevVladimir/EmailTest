package pages;

import exception.MessageException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class MessagePage extends BasePage {

    public MessagePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@class='hP']")
    private WebElement subject;

    @FindBy(xpath = "//*[@class='qu']/span")
    private WebElement author;

    @FindBy(xpath = "//*[@class = 'a3s aXjCH ']")
    private WebElement message;

    @FindBy(xpath = "//*[@class = 'gb_D gb_Ua gb_i']")
    private WebElement accountImage;

    @FindBy(xpath = "//*[@class = 'gb_Mb gb_mg gb_ug gb_7e gb_7c']")
    private WebElement exitButton;

    public String getSubjectText() {
        return subject.getText();
    }

    public String getAuthorText() {
        return author.getText();
    }

    public String getMessageText() {
        return message.getText();
    }

    /**
     * Метод проверки письма. Метод проверяет автора письма, тему и делает проверку на содержание письма(то что оно есть).
     * Письма без содержания считаются не валидными. Будет выброшено исключение.
     *
     * @param author
     * @param subject
     * @throws MessageException
     */
    public void checkMessagePage(String author, String subject) throws MessageException {
        Assert.assertEquals(getSubjectText(), subject);
        Assert.assertEquals(getAuthorText(), author);
        try {
            Assert.assertFalse(getMessageText().isEmpty());
        } catch (NoSuchElementException e) {
            throw new MessageException("Письмо без содержания");
        }
    }

    public void clickAccountImage() {
        accountImage.click();
    }

    public void clickExitButton() {
        waitForElementPresent(exitButton, driver);
        exitButton.click();
    }

    public void exitAccount() {
        clickAccountImage();
        clickExitButton();
    }
}
