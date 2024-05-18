package com.artyom.bookstore.controller;

import com.artyom.bookstore.entity.Book;
import com.artyom.bookstore.repository.BookCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class BookStoreController {
    private final BookCollection bookCollection;

    @GetMapping("/book")
    public Book getBookByName(@RequestParam String name) {
        delayOf5Secs();
        return bookCollection.findBook(name);
    }

    private void delayOf5Secs() {
        try {
            Thread.sleep(Duration.ofSeconds(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
