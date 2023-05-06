package com.example.mydividend.persist.entity;

import com.example.mydividend.model.Dividend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DIVIDEND")
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private LocalDateTime date;
    private String dividend;

    public static DividendEntity of(Long companyId, Dividend dividend) {
        return DividendEntity.builder()
                .companyId(companyId)
                .dividend(dividend.getDividend())
                .date(dividend.getDate())
                .build();
    }

}
