package com.example.mydividend.service;

import com.example.mydividend.model.Autocompletion;
import com.example.mydividend.model.Company;
import com.example.mydividend.model.ScrapedResult;
import com.example.mydividend.persist.CompanyRepository;
import com.example.mydividend.persist.DividendRepository;
import com.example.mydividend.persist.entity.CompanyEntity;
import com.example.mydividend.persist.entity.DividendEntity;
import com.example.mydividend.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Company> getAllCompany(Pageable pageable) {
        // list -> page 변경 : new PageImpl<>(list);
        return new PageImpl<>(companyRepository.findAll(pageable)
                .stream()
                .map(Company::from)
                .collect(Collectors.toList()));
    }

    public List<String> getCompanyNamesByKeyword(String keyword) {
        Pageable pageable = PageRequest.of(0, 10);
        return companyRepository.findByNameStartingWithIgnoreCase(keyword, pageable)
                .stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toList());
    }
}
