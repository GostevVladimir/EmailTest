import manager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class TestBase {

    protected final ApplicationManager applicationManager = new ApplicationManager();

    @BeforeMethod
    public void setupPage() throws IOException {
        applicationManager.init();
    }

    @AfterMethod(alwaysRun = true)
    public void closePage() {
        applicationManager.close();
    }
}
