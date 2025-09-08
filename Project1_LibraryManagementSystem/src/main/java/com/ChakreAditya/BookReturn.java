package com.ChakreAditya;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookReturn {

    public void ReturnBook(Session sess4) {
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

            break;
        }

        TypedQuery<Member> query = sess4.createQuery(
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
        }catch(NullPointerException e){
            System.out.println("No member found with given name and phone or input is Null.");
            return;
        }


        System.out.println("Enter ISBN of book: ");

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

        Book result = sess4.find(Book.class, isbn);

        if (result == null) {
            System.out.println("❌ No available book found with ISBN: " + isbn);
        } else {
            System.out.println("\nBook Found: " + result.getTitle() + " by " + result.getAuthor());

            assert member != null;
            result.setAvailability(true);

            List<Book>borrowedBooks = member.getBorrowedBooks();
            if (borrowedBooks != null) {
                borrowedBooks.remove(result);
                member.setBorrowedBooks(borrowedBooks);
            }

            Transaction tx = sess4.beginTransaction();
            sess4.merge(member);
            sess4.merge(result);
            tx.commit();

            System.out.println("✅ Book returned successfully.");

        }

    }
}
