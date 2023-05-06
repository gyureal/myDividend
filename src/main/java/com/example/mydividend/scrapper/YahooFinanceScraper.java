package com.example.mydividend.scrapper;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.Dividend;
import com.example.mydividend.model.ScrapedResult;
import com.example.mydividend.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class YahooFinanceScraper implements Scrapper {

    private static final String BASE_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400; // 60 * 60 * 24

    @Override
    public ScrapedResult scrap(Company company) throws IOException {
        List<Dividend> dividends = new ArrayList<>();
        try {
            long now = System.currentTimeMillis() / 1000;
            String url = String.format(BASE_URL, company.getTicker(), START_TIME, now);
            System.out.println(url);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableElement = parsingDivs.get(0);

            Element tbody = tableElement.children().get(1);

            for(Element ele : tbody.children()) {
                String text = ele.text();
                if (!text.endsWith("Dividend")) {
                    String[] splits = text.split(" ");
                    int month = Month.strToNumber(splits[0]);
                    int day = Integer.parseInt(splits[1].replace(",", ""));
                    int year = Integer.parseInt(splits[2]);
                    String dividend = splits[3];

                    dividends.add(Dividend.builder()
                            .date(LocalDateTime.of(year, month, day, 0, 0))
                            .dividend(dividend)
                            .build());
                }
            }
            return new ScrapedResult(company, dividends);

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("scrap 중 에러가 발생했습니다.", e);
        }
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) throws IOException {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split("\\(")[0].replace(")", "").trim();

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("scrapCompanyByTicker 에러", e);
        }
    }
}
