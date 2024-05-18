package com.artyom.bestpricebookstore.controller;

import com.artyom.bestpricebookstore.entity.BestPriceResult;
import com.artyom.bestpricebookstore.entity.Book;
import com.artyom.bestpricebookstore.service.BookRetrievalService;
import com.artyom.bestpricebookstore.service.RestCallStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/virtual-store")
public class BestPriceBookController {

    public static final ScopedValue<RestCallStatistics> TIMEMAP = ScopedValue.newInstance();

    private final BookRetrievalService bookRetrievalService;


    @GetMapping("/book")
    public BestPriceResult getBestPriceForBook(@RequestParam String name) {
        long start = System.currentTimeMillis();

        RestCallStatistics timeObj = new RestCallStatistics();
        try {
            List<Book> books = ScopedValue.callWhere(TIMEMAP, timeObj, () -> bookRetrievalService.getBookFromAllStores(name));

            Book bestPriceBook = books.stream()
                    .min(Comparator.comparing(Book::cost))
                    .orElseThrow();

            return new BestPriceResult(timeObj, bestPriceBook, books);
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception while calling getBestPrice", e);
        } catch (Exception e) {
            throw new RuntimeException("Exception while getting TIMeMAP", e);
        } finally {
            long end = System.currentTimeMillis();
            timeObj.addTiming("Best Price Store", end - start);
            timeObj.dumpTiming();
        }
    }
}
