package com.example.mydividend.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Company {
    private String ticker;
    private String name;
}
