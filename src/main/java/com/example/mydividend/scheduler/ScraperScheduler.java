package com.example.mydividend.scheduler;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.ScrapedResult;
import com.example.mydividend.persist.CompanyRepository;
import com.example.mydividend.persist.DividendRepository;
import com.example.mydividend.persist.entity.CompanyEntity;
import com.example.mydividend.persist.entity.DividendEntity;
import com.example.mydividend.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scrapper yahooScrapper;

    @Scheduled(cron = "0 0 0 * * *")
    public void yahooScheduling() throws IOException {
        log.info("scrapping started");
        List<CompanyEntity> companies = companyRepository.findAll();
        for (var company : companies) {
            ScrapedResult scrapedResult = yahooScrapper.scrap(new Company(company.getName(), company.getTicker()));

            scrapedResult.getDividends().stream()
                    .map(dividend -> DividendEntity.of(company.getId(), dividend))
                    .forEach(e -> {
                        if(!this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate())) {
                            dividendRepository.save(e);
                        }
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


    }
}
