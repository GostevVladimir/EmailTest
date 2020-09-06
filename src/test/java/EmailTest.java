import exception.MessageException;
import org.testng.annotations.Test;

public class EmailTest extends TestBase {
    public String messageAuthor = "Карвалол Карвалолов";
    public String messageSubject = "777777";
    public String headerText = "Выберите аккаунт";

    @Test
    public void checkMessage() throws MessageException {
        applicationManager.getEmailPage().openMessage(messageAuthor, messageSubject);
        applicationManager.getMessagePage().checkMessagePage(messageAuthor, messageSubject);
        applicationManager.getMessagePage().exitAccount();
        applicationManager.getLoginPage().checkHeaderText(headerText);
    }
}
