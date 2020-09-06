package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected final static int WAITING_TIME_IN_SECONDS = 30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void click(WebElement element) {
        element.click();
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public static WebElement waitForElementPresent(WebElement element, WebDriver driver) {
        return (new WebDriverWait(driver, WAITING_TIME_IN_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static Boolean waitInvisibilityNewMessage(WebDriver driver) {
        return (new WebDriverWait(driver, WAITING_TIME_IN_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='aRI']")));
    }

    protected void inputText(WebElement locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = locator.getAttribute("value");
            if (!text.equals(existingText)) {
                locator.clear();
                locator.sendKeys(text);
            }
        }
    }


    public static String dependableGetFontWeight(WebDriver driver, WebElement element) {
        final int MAXIMUM_WAIT_TIME = 15;
        final int MAX_STALE_ELEMENT_RETRIES = 10;

        WebDriverWait wait = new WebDriverWait(driver, MAXIMUM_WAIT_TIME);
        int retries = 0;
        while (true) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                return element.findElement(By.xpath(".//div[2]/span/span[@class]")).getCssValue("font-weight");
            } catch (StaleElementReferenceException e) {
                if (retries < MAX_STALE_ELEMENT_RETRIES) {
                    retries++;
                    continue;
                } else {
                    throw e;
                }
            }
        }
    }
}
