package com.example.mydividend.scrapper;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.ScrapedResult;

import java.io.IOException;

public interface Scrapper {
    Company scrapCompanyByTicker(String ticker) throws IOException;
    ScrapedResult scrap(Company company) throws IOException;
}
