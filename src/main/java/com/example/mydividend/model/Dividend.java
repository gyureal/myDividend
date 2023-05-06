package com.example.mydividend.model;

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
}
