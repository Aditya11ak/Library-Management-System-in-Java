package com.ChakreAditya;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        SessionFactory sf = new Configuration()
                .configure()
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Member.class)
                .buildSessionFactory();

        System.out.println("Hi! Welcome to our Library Management System: 😊\n");
        System.out.println("\nPress the number for functionality u Want: \n");

        do {
            System.out.println("1) Admin Access");
            System.out.println("2) Search for a Book");
            System.out.println("3) Issue Book to a member");
            System.out.println("4) Return Book");
            System.out.println("5) Exit");

            // Input for Choice
            System.out.println();
            while (true) {
                try {
                    choice = sc.nextInt();
                    if (choice > 0 && choice <= 5) {
                        sc.nextLine();
                        break;
                    } else {
                        System.out.println("The Number should be in given range! ");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Does not match the Integer or It's out of range");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Something went wrong!");
                    sc.nextLine();
                }
            }

            switch (choice) {

                case 1:
                    CheckPassword p = new CheckPassword();
                    if (p.EnterPass()) {
                        System.out.println("Access Granted! ");
                        System.out.println();
                    } else {
                        System.out.println("Access Denied! ");
                        System.out.println();
                        break;
                    }
                    Session sess = sf.openSession();
                    AdminAccess a = new AdminAccess();
                    a.Admin(sess);
                    sess.close();
                    break;

                case 2:
                    Session sess2 = sf.openSession();
                    SearchForBook sb = new SearchForBook();
                    sb.SearchBok(sess2);
                    sess2.close();
                    break;

                case 3:

                    Session sess3 = sf.openSession();
                    BookIssue ib = new BookIssue();
                    ib.IssueBook(sess3);
                    sess3.close();
                    break;

                case 4:
                    Session sess4 = sf.openSession();
                    BookReturn br = new BookReturn();
                    br.ReturnBook(sess4);
                    sess4.close();
                    break;

                case 5:
                    sf.close();
                    System.out.println("Thank you, Happy Reading! 😊");
                    break;

                default:
                    System.out.println("Something went wrong! ");
                    break;


            }
        } while (choice != 5);

    }
}