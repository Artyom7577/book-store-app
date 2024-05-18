package com.artyom.bookstore.repository;

import com.artyom.bookstore.entity.Book;

public interface BookCollection {
    Book findBook(String name);
}
