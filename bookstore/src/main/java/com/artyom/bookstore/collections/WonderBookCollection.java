package com.artyom.bookstore.collections;

import com.artyom.bookstore.entity.Book;
import com.artyom.bookstore.repository.BookCollection;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Component
@Profile("WONDER")
public class WonderBookCollection implements BookCollection {

    private final ArrayList<Book> books = new ArrayList<>();
    @Value("${book.store.name}")
    private String storeName;

    @PostConstruct
    public void initialize() {
        books.add(new Book(storeName, "And Then There Were None", "Agatha Christie", 10, 300, bookUrl("And Then There Were None")));
        books.add(new Book(storeName, "To Kill a Mockingbird", "Harper Lee", 15, 320, bookUrl("To Kill a Mockingbird")));
        books.add(new Book(storeName, "1984", "George Orwell", 12, 250, bookUrl("1984")));
        books.add(new Book(storeName, "The Great Gatsby", "F. Scott Fitzgerald", 8, 200, bookUrl("The Great Gatsby")));
        books.add(new Book(storeName, "Pride and Prejudice", "Jane Austen", 20, 350, bookUrl("Pride and Prejudice")));
        books.add(new Book(storeName, "The Catcher in the Rye", "J.D. Salinger", 18, 280, bookUrl("The Catcher in the Rye")));
        books.add(new Book(storeName, "Harry Potter and the Philosopher's Stone", "J.K. Rowling", 25, 400, bookUrl("Harry Potter and the Philosopher's Stone")));
        books.add(new Book(storeName, "The Hobbit", "J.R.R. Tolkien", 22, 360, bookUrl("The Hobbit")));
        books.add(new Book(storeName, "The Da Vinci Code", "Dan Brown", 16, 420, bookUrl("The Da Vinci Code")));
        books.add(new Book(storeName, "The Alchemist", "Paulo Coelho", 14, 180, bookUrl("The Alchemist")));
        books.add(new Book(storeName, "The Lord of the Rings", "J.R.R. Tolkien", 30, 1200, bookUrl("The Lord of the Rings")));
        books.add(new Book(storeName, "Gone with the Wind", "Margaret Mitchell", 11, 960, bookUrl("Gone with the Wind")));
        books.add(new Book(storeName, "The Chronicles of Narnia", "C.S. Lewis", 19, 800, bookUrl("The Chronicles of Narnia")));
        books.add(new Book(storeName, "Moby-Dick", "Herman Melville", 9, 560, bookUrl("Moby-Dick")));
        books.add(new Book(storeName, "The Picture of Dorian Gray", "Oscar Wilde", 13, 240, bookUrl("The Picture of Dorian Gray")));
        books.add(new Book(storeName, "The Hunger Games", "Suzanne Collins", 17, 400, bookUrl("The Hunger Games")));
        books.add(new Book(storeName, "The Shining", "Stephen King", 20, 450, bookUrl("The Shining")));
        books.add(new Book(storeName, "The Girl with the Dragon Tattoo", "Stieg Larsson", 14, 560, bookUrl("The Girl with the Dragon Tattoo")));
        books.add(new Book(storeName, "The Road", "Cormac McCarthy", 10, 320, bookUrl("The Road")));
        books.add(new Book(storeName, "The Kite Runner", "Khaled Hosseini", 15, 400, bookUrl("The Kite Runner")));
        books.add(new Book(storeName, "Brave New World", "Aldous Huxley", 11, 340, bookUrl("Brave New World")));
        books.add(new Book(storeName, "The Grapes of Wrath", "John Steinbeck", 12, 480, bookUrl("The Grapes of Wrath")));
        books.add(new Book(storeName, "The Odyssey", "Homer", 18, 560, bookUrl("The Odyssey")));
        books.add(new Book(storeName, "The Adventures of Huckleberry Finn", "Mark Twain", 16, 400, bookUrl("The Adventures of Huckleberry Finn")));
        books.add(new Book(storeName, "Jane Eyre", "Charlotte Brontë", 21, 450, bookUrl("Jane Eyre")));
        books.add(new Book(storeName, "The Secret Garden", "Frances Hodgson Burnett", 10, 280, bookUrl("The Secret Garden")));
        books.add(new Book(storeName, "Les Misérables", "Victor Hugo", 23, 1200, bookUrl("Les Misérables")));
        books.add(new Book(storeName, "The Count of Monte Cristo", "Alexandre Dumas", 14, 900, bookUrl("The Count of Monte Cristo")));
        books.add(new Book(storeName, "A Tale of Two Cities", "Charles Dickens", 25, 480, bookUrl("A Tale of Two Cities")));
        books.add(new Book(storeName, "Wuthering Heights", "Emily Brontë", 20, 400, bookUrl("Wuthering Heights")));
    }


    private String bookUrl(String title) {
        try {
            return String.format(
                    "http://wonder:8081/store/book?name=%s",
                    URLEncoder.encode(title, StandardCharsets.UTF_8));
        } catch (Exception e) {
            return "?";
        }
    }

    @Override
    public Book findBook(String name) {

        return books.stream()
                .filter(b -> b.bookName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }
}
