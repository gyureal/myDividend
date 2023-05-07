package com.example.mydividend.service;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.ScrapedResult;
import com.example.mydividend.persist.CompanyRepository;
import com.example.mydividend.persist.DividendRepository;
import com.example.mydividend.persist.entity.CompanyEntity;
import com.example.mydividend.persist.entity.DividendEntity;
import com.example.mydividend.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final Scrapper yahooFinanceScrapper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) throws IOException {
        if(companyRepository.existsByTicker(ticker)) {
            throw new IllegalArgumentException("이미 존재하는 Ticker 입니다.");
        }
        return storeCompanyAndDividend(ticker);
    }

    private Company storeCompanyAndDividend(String ticker) throws IOException {
        // 회사 스크래핑
        Company company = yahooFinanceScrapper.scrapCompanyByTicker(ticker);

        // 회사가 존재할 경우 배당금 정보 스크래핑
        ScrapedResult scrapedResult = yahooFinanceScrapper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = companyRepository.save(CompanyEntity.from(company));
        dividendRepository.saveAll(scrapedResult.getDividends().stream()
                .map(e -> DividendEntity.of(companyEntity.getId(), e))
                .collect(Collectors.toList()));

        return company;
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll().stream()
                .map(Company::from)
                .collect(Collectors.toList());
    }
}
