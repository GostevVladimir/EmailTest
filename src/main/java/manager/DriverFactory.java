package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

public class DriverFactory {

    static WebDriver createDriver(String browser) {
        if (browser.equals(BrowserType.CHROME)) {
            return new ChromeDriver();
        } else if (browser.equals(BrowserType.FIREFOX)) {
            return new FirefoxDriver();
        }
        return new ChromeDriver();
    }
}
