package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import pages.EmailPage;
import pages.LoginPage;
import pages.MessagePage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    public WebDriver driver;
    public LoginPage loginPage;
    public EmailPage emailPage;
    public MessagePage messagePage;

    public void init() throws IOException {
        Properties properties = new Properties();
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/main/resources/%s.properties", target))));
        driver = DriverFactory.createDriver(System.getProperty("browser", BrowserType.CHROME));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(properties.getProperty("web.baseUrl"));
        loginPage = new LoginPage(driver);
        emailPage = new EmailPage(driver);
        messagePage = new MessagePage(driver);

        loginPage.login(properties.getProperty("web.login"), properties.getProperty("web.password"));
    }

    public void close() {
        driver.quit();
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public EmailPage getEmailPage() {
        return emailPage;
    }

    public MessagePage getMessagePage() {
        return messagePage;
    }
}
