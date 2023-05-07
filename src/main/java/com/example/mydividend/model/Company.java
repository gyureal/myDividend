package com.example.mydividend.model;

import com.example.mydividend.persist.entity.CompanyEntity;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Company {
    private String ticker;
    private String name;

    public static Company from(CompanyEntity companyEntity) {
        return Company.builder()
                .ticker(companyEntity.getTicker())
                .name(companyEntity.getName())
                .build();
    }
}
