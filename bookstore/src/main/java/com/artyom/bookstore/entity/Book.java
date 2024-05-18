package com.artyom.bookstore.entity;

public record Book(String bookStory,
                   String bookName,
                   String author,
                   int cost,
                   int numPages,
                   String link) {
}
