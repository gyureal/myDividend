package com.example.mydividend.persist.entity;

import com.example.mydividend.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "COMPANY")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

    public static CompanyEntity from(Company company) {
        return CompanyEntity.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build();
    }
}
