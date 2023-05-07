package com.example.mydividend.model;

import com.example.mydividend.persist.entity.DividendEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Dividend {
    private LocalDateTime date;
    private String dividend;

    public static Dividend from(DividendEntity dividendEntity) {
        return new Dividend(dividendEntity.getDate(),
                dividendEntity.getDividend());
    }
}
