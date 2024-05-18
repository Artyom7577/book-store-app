package com.artyom.bestpricebookstore.service;

import com.artyom.bestpricebookstore.controller.BestPriceBookController;
import com.artyom.bestpricebookstore.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;


@Service
public class BookRetrievalService {

    @Value("#{${book.store.baseurls}}")
    private Map<String, String> storeUrlMap;

    private RestClient restClient = RestClient.create();

    public List<Book> getBookFromAllStores(String bookName) throws InterruptedException {
        try (var scope = new StructuredTaskScope<Book>()) {

            List<Subtask<Book>> bookTasks = new ArrayList<>();
            storeUrlMap.forEach((name, url) -> {
                bookTasks.add(scope.fork(() -> getBookFromStore(name, url, bookName)));
            });

            scope.join();

            bookTasks.stream()
                    .filter(t -> t.state() == Subtask.State.FAILED)
                    .map(Subtask::exception)
                    .forEach(e -> e.printStackTrace());

            return bookTasks.stream()
                    .filter(t -> t.state() == Subtask.State.SUCCESS)
                    .map(Subtask::get)
                    .toList();
        }
    }

    private Book getBookFromStore(String name, String url, String bookName) {
        long start = System.currentTimeMillis();

        Book book = restClient.get()
                .uri(url + "/store/book", t -> t.queryParam("name", bookName).build())
                .retrieve()
                .body(Book.class);

        long end = System.currentTimeMillis();
        RestCallStatistics timeObj = BestPriceBookController.TIMEMAP.get();
        timeObj.addTiming(name, end - start);

        return book;
    }

}
