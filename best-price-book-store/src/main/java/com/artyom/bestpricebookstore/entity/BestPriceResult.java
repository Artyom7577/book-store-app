package com.artyom.bestpricebookstore.entity;

import com.artyom.bestpricebookstore.service.RestCallStatistics;

import java.util.List;

public record BestPriceResult(RestCallStatistics restCallStatistics, Book bestPriceDeal, List<Book> allDeals) {

}
