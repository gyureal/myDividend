package com.example.mydividend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapedResult {
    private Company company;
    private List<Dividend> dividends = new ArrayList<>();
}
