package com.example.mydividend.web;

import com.example.mydividend.model.Autocompletion;
import com.example.mydividend.model.Company;
import com.example.mydividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final Autocompletion autocompletion = new Autocompletion();
    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
       // return ResponseEntity.ok(autocompletion.autocomplete(keyword));
        return ResponseEntity.ok(companyService.getCompanyNamesByKeyword(keyword));
    }

    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) throws IOException {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new IllegalArgumentException("ticker is empty");
        }
        Company company = companyService.save(ticker);
        //autocompletion.addKeyword(company.getName());
        return ResponseEntity.ok(company);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
