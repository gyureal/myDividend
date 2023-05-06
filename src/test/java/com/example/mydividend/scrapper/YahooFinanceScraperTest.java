package com.example.mydividend.scrapper;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.ScrapedResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class YahooFinanceScraperTest {

    @Test
    void 스크래핑_성공() throws IOException {
        // given
        YahooFinanceScraper yahooFinanceScraper = new YahooFinanceScraper();

        // when
        ScrapedResult scrap = yahooFinanceScraper.scrap(new Company("COKE", "코카콜라"));

        // then
        assertThat(scrap).isNotNull();
        System.out.println(scrap);
    }

    @Test
    void scrapCompanyByTicker_성공() throws IOException {
        // given
        YahooFinanceScraper yahooFinanceScraper = new YahooFinanceScraper();

        // when
        Company result = yahooFinanceScraper.scrapCompanyByTicker("MMM");

        // then
        assertThat(result).isNotNull();
        System.out.println(result);
    }
}