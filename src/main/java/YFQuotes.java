import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Collections;

public class YFQuotes {

    public static OptionChain getYFQuotes(String url){

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(new By.ByXPath("//button[@type='submit' and @class='btn primary' and @name='agree']")).click();
        Document document = Jsoup.parse(driver.getPageSource());// = driver.findElement(new By.ByXPath("//section[@data-reactid='2']"));
        Double priceOfUnderl = Double.parseDouble(document.select("fin-streamer[data-reactid=29]").text().replaceAll(",", ""));
        Elements calls = document.select("table.calls");
        Elements puts = document.select("table.puts");
        Elements calls_rows = calls.select("tr[class^=data-row]");
        Elements puts_rows = puts.select("tr[class^=data-row]");
        OptionChain chain = new OptionChain(priceOfUnderl);
        for (Element x : calls_rows){
            double strike = Double.parseDouble(x.select("td[class^=data-col2]").text().replaceAll(",", ""));
            double bid = Double.parseDouble(x.select("td[class^=data-col4]").text().replaceAll(",", ""));
            double ask = Double.parseDouble(x.select("td[class^=data-col5]").text().replaceAll(",", ""));
            //double implVol = Double.parseDouble(x.select("td[class^=data-col10]").text().replace('%', ' '));
            chain.addOption(new Option(strike, 'C', ask, bid));
        }
        for (Element x : puts_rows){
            double strike = Double.parseDouble(x.select("td[class^=data-col2]").text().replaceAll(",", ""));
            double bid = Double.parseDouble(x.select("td[class^=data-col4]").text().replaceAll(",", ""));
            double ask = Double.parseDouble(x.select("td[class^=data-col5]").text().replaceAll(",", ""));
            //double implVol = Double.parseDouble(x.select("td[class^=data-col10]").text().replace('%', ' '));
            chain.addOption(new Option(strike, 'P', ask, bid));
        }
        Collections.sort(chain.getStrikes());
        return chain;
    }
}
