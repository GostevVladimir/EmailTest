package helpers;

import exception.MessageException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static pages.BasePage.dependableGetFontWeight;

public class TableHelper {

    private WebElement tableElement;
    private WebDriver driver;

    public TableHelper(WebElement tableElement, WebDriver driver) {
        this.tableElement = tableElement;
        this.driver = driver;
    }

    public List<WebElement> getRows() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tr"));
        return rows;
    }

    public List<List<WebElement>> getRowsWithColumns() {
        List<WebElement> rows = getRows();
        List<List<WebElement>> rowsWithColumns = new ArrayList<List<WebElement>>();
        for (WebElement row : rows) {
            List<WebElement> rowWithColumns = row.findElements(By.xpath(".//td"));
            rowsWithColumns.add(rowWithColumns);
        }
        return rowsWithColumns;
    }

    /**
     * Метод поиска письма.
     * Метод принимает на вход:
     *
     * @param messageAuthor  - Автор сообщения
     * @param messageSubject - Тема сообщения
     *                       Поскольку критерии поиска в задание явно не указаны, то предполагается что в ящике не может быть больше
     *                       двух писем от одного и того же автора с одинаковыми соообщениями(если таких писем больше 2 будет выброшено иссключение), но может быть сколько
     *                       угодно сообщений от разных авторов с разными темами, ну или от одинаковых авторов но с разными темами.
     *                       Метод работает следующим образом если есть два сообщения от одного и того же автора с одинаковой темой,
     *                       то метод вернет непрочитаное ранее сообщение(Выделеное жирным шрифтом на веб форме. За это отвечет свойство font-weight.
     *                       Если font-weight = 700 то письмо не прочитано(выделено жирным). У прочитаного письма font-weight = 400). Если же оба сообщения
     *                       или прочитаны или не прочитаными то откроется первое из них. Метод можно доработать добавив в критерии поиска время получения письма, но опять же
     *                       письма с одинаковыми авторами и темами могут прийти в одно и тоже время.
     * @return - Сообщение
     */
    public WebElement getMessage(String messageAuthor, String messageSubject) throws MessageException {
        List<WebElement> messagesList = new ArrayList<>();
        List<WebElement> oldMessagesList = new ArrayList<>();
        List<List<WebElement>> rowsWithColumns = getRowsWithColumns();

        for (int i = 0; i < rowsWithColumns.size(); i++) {
            if ((rowsWithColumns.get(i).get(3).getText().equals(messageAuthor)) &
                    (rowsWithColumns.get(i).get(4).getText().contains(messageSubject))) {
                messagesList.add(rowsWithColumns.get(i).get(3));
            }
        }
        if(messagesList.size() == 0)
            throw new MessageException("Почтовый ящик пуст или по заданым критериям поиска нет писем");
        for (int i = 0; i < messagesList.size(); i++) {
            if (messagesList.size() > 2) {
                throw new MessageException("В почтовом ящике больше 2 сообщений от одного автора с одинаковыми темами. Удалите лишние письма.");
            } else if ((dependableGetFontWeight(driver, messagesList.get(i)).equals("700"))) {
                return messagesList.get(i);
            } else oldMessagesList.add(messagesList.get(i));
        }
        return oldMessagesList.get(0);
    }
}
