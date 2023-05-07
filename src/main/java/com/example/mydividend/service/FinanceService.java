package com.example.mydividend.service;

import com.example.mydividend.model.Company;
import com.example.mydividend.model.Dividend;
import com.example.mydividend.model.ScrapedResult;
import com.example.mydividend.persist.CompanyRepository;
import com.example.mydividend.persist.DividendRepository;
import com.example.mydividend.persist.entity.CompanyEntity;
import com.example.mydividend.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public ScrapedResult getDividendByCompanyName(String companyName) {

        CompanyEntity companyEntity = companyRepository.findByName(companyName)
                .orElseThrow(() -> new IllegalArgumentException("회사명이 없습니다."));

        List<DividendEntity> dividendEntities
                = dividendRepository.findAllByCompanyId(companyEntity.getId());

        return ScrapedResult.builder()
                .company(Company.from(companyEntity))
                .dividends(dividendEntities.stream().map(Dividend::from).collect(Collectors.toList()))
                .build();
    }
}
