package com.ChakreAditya;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookIssue {

    public void IssueBook(Session sess3) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nInsert your details.");
        String name;
        while (true) {
            System.out.print("name: ");
            name = sc.nextLine();
            name = name.toLowerCase();
            if (name.isBlank()) {
                System.out.println("name cannot be empty.");
                continue;
            }
            if (name.matches(".*\\d.*")) {
                System.out.println("name cannot contain any number: ");
                continue;
            }
            break;
        }
        String phone;
        while (true) {
            System.out.print("Enter phone number: ");
            phone = sc.nextLine().trim();

            if (phone.isEmpty()) {
                System.out.println("Phone number cannot be empty.");
                continue;
            }

            if (!phone.matches("^[0-9\\- ]+$")) {
                System.out.println("Phone number can only contain digits, spaces, and hyphens (-).");
                continue;
            }

            String digitsOnly = phone.replaceAll("\\D", "");
            if (digitsOnly.length() != 10) {
                System.out.println("Phone number must contain exactly 10 digits.");
                continue;
            }
            if (!phone.matches(".*\\d.*")) {
                System.out.println("phone cannot contain any characters: ");
                continue;
            }
            break;
        }

        TypedQuery<Member> query = sess3.createQuery(
                "FROM Member WHERE name = :name AND phone = :phone", Member.class);
        query.setParameter("name", name);
        query.setParameter("phone", phone);

        Member member = null;
        try {
            member = query.getSingleResult();
            System.out.println();
            System.out.println(member);
            System.out.println();
        } catch (NoResultException e) {
            System.out.println("No member found with given name and phone.");
            return;
        } catch (NullPointerException e) {
            System.out.println("No member found with given name and phone or the entry is null. ");
            return;
        }

        System.out.println("Search for Book by ISBN");

        String isbn;
        while (true) {
            System.out.print("Enter ISBN: ");
            isbn = sc.nextLine().trim();

            if (isbn.isEmpty()) {
                System.out.println("ISBN cannot be empty.");
                continue;
            }

            if (!isbn.matches("^[a-zA-Z0-9]+$")) {
                System.out.println("ISBN must not contain spaces or symbols.");
                continue;
            }

            break;
        }

        Book result = sess3.find(Book.class, isbn); // Direct lookup using primary key

        if (result == null || !result.getAvailability()) {
            System.out.println("❌ No available book found with ISBN: " + isbn);
        } else {
            System.out.println("\nBook Found: " + result.getTitle() + " by " + result.getAuthor());
            System.out.println("Do you want to issue this book? (y/n)");

            String checking;
            while (true) {
                checking = sc.nextLine().trim().toLowerCase();
                if (checking.equals("y") || checking.equals("n")) break;
                System.out.println("Only 'y' or 'n' is allowed!");
            }

            if (checking.equals("y")) {
                assert member != null;

                List<Book> borrowedBooks = member.getBorrowedBooks();

                if (borrowedBooks == null) {
                    borrowedBooks = new ArrayList<>();
                }

                if (member.getBorrowedBooks() != null && member.getBorrowedBooks().size() >= 4) {
                    System.out.println("❌ You cannot issue more than 4 books.");
                    return;
                }

                borrowedBooks.add(result); // Add new book to existing list
                member.setBorrowedBooks(borrowedBooks);

                result.setAvailability(false);

                Transaction tx = sess3.beginTransaction();
                sess3.merge(member);
                sess3.merge(result);
                tx.commit();

                System.out.println("✅ Book issued successfully.");
            } else {
                System.out.println("❌ Book not issued.");
            }

        }
    }

}
