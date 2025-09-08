package com.ChakreAditya;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class SearchForBook {
    public void SearchBok(Session sess2){
        Scanner sc = new Scanner(System.in);
        System.out.println("Search for Books by Title");

        String search;
        while (true) {
            System.out.print("Enter book title or part of it: ");
            search = sc.nextLine().trim();

            if (search.isEmpty()) {
                System.out.println("Search term cannot be empty.");
                continue;
            }

            if (!search.matches("^[a-zA-Z ]+$")) {
                System.out.println("Search term must not contain numbers or symbols.");
                continue;
            }

            break;
        }

        TypedQuery<Book> query = sess2.createQuery(
                "FROM Book WHERE LOWER(Title) LIKE :search", Book.class);
        query.setParameter("search", "%" + search.toLowerCase() + "%");

        List<Book> results = query.getResultList();

        if (results.isEmpty()) {
            System.out.println("No books found matching the title: \"" + search + "\"");
        } else {
            System.out.println("\nBooks Found:\n");

            // Print table header
            System.out.printf("%-15s %-35s %-25s %-8s %-20s %-15s %-10s %-10s%n",
                    "ISBN", "Title", "Author", "Volume", "Publisher", "Genre", "Edition", "Available");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

            for (Book book : results) {
                System.out.printf("%-15s %-35s %-25s %-8s %-20s %-15s %-10s %-10s%n",
                        book.getISBN(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getVolume(),
                        book.getPublisher(),
                        book.getGenre(),
                        book.getEdition(),
                        (book.getAvailability() ? "Yes" : "No"));
            }
        }
        System.out.println();


    }
}
