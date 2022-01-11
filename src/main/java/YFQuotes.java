import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class YFQuotes {
    public YFQuotes(String url){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(new By.ByXPath("//button[@type='submit' and @class='btn primary' and @name='agree']")).click();
        Document document = Jsoup.parse(driver.getPageSource());// = driver.findElement(new By.ByXPath("//section[@data-reactid='2']"));
        Elements calls = document.select("table.calls");
        Elements puts = document.select("table.puts");
        Elements calls_rows = calls.select("tr[class^=data-row]");
        Elements puts_rows = puts.select("tr[class^=data-row]");
    }
}
