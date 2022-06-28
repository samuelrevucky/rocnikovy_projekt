import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Collections;

public class YFQuotes {

    /**
     * Static method specifically suited to retrieve option chains from Yahoo Finance page.
     *
     * @param url Url of the YF page of the wanted option.
     * @return OptionChain
     */

    public static OptionChain getYFQuotes(String url) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(new By.ByXPath("//button[@type='submit' and @class='btn primary' and @name='agree']")).click();
        Document document = Jsoup.parse(driver.getPageSource());
        driver.close();

        Double priceOfUnderl = Double.parseDouble(document.select("fin-streamer[data-test=qsp-price]").first().text().replaceAll(",", ""));
        Elements calls_rows = document.select("table.calls").select("tr[class^=data-row]");
        Elements puts_rows = document.select("table.puts").select("tr[class^=data-row]");
        OptionChain chain = new OptionChain(priceOfUnderl);

        for (Element x : calls_rows){
            double strike = Double.parseDouble(x.select("td[class^=data-col2]").text().replaceAll(",", ""));
            double bid = Double.parseDouble(x.select("td[class^=data-col4]").text().replaceAll(",", ""));
            double ask = Double.parseDouble(x.select("td[class^=data-col5]").text().replaceAll(",", ""));
            //double implVol = Double.parseDouble(x.select("td[class^=data-col10]").text().replace('%', ' '));
            chain.addOption(new Option(strike, Option.Right.Call, ask, bid));
        }
        for (Element x : puts_rows){
            double strike = Double.parseDouble(x.select("td[class^=data-col2]").text().replaceAll(",", ""));
            double bid = Double.parseDouble(x.select("td[class^=data-col4]").text().replaceAll(",", ""));
            double ask = Double.parseDouble(x.select("td[class^=data-col5]").text().replaceAll(",", ""));
            //double implVol = Double.parseDouble(x.select("td[class^=data-col10]").text().replace('%', ' '));
            chain.addOption(new Option(strike, Option.Right.Put, ask, bid));
        }
        return chain;
    }
}
