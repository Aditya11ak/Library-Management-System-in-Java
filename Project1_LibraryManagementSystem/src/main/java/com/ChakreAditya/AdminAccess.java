package com.ChakreAditya;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.*;

public class AdminAccess {
    public void Admin(Session sess){
        int option;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1) Insert Book Details");
            System.out.println("2) Remove Book");
            System.out.println("3) Insert Member Details");
            System.out.println("4) Check Member Details");
            System.out.println("5) Remove Member");
            System.out.println("6) Exit");
            while (true) {
                try {
                    option = sc.nextInt();
                    if (option > 0 && option <= 6) {
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
            switch (option) {
                case 1:
                    System.out.println("\nHow many books do u want to insert? ");
                    int n;
                    while (true) {
                        try {
                            n = sc.nextInt();
                            if (n > 0 && n <= 10) {
                                sc.nextLine();
                                break;
                            } else {
                                System.out.println("The Number should be positive! and less than 10 ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Does not match the Integer or It's out of range");
                            sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("Something went wrong!");
                            sc.nextLine();
                        }
                    }
                    for (int i = 0; i < n; i++) {
                        while (true) {
                            System.out.println("\nEnter details for Book " + (i + 1) + ":");

                            String ISBN;
                            while (true) {
                                System.out.print("ISBN: ");
                                ISBN = sc.nextLine().trim();

                                if (ISBN.isEmpty()) {
                                    System.out.println("ISBN cannot be empty.");
                                    continue;
                                }
                                if (!ISBN.matches("^[^-_\\s]+$")) {
                                    System.out.println("Input must not contain hyphens (-), underscores (_), or spaces.");
                                    continue;
                                }
                                if (sess.find(Book.class, ISBN) != null) {
                                    System.out.println("ISBN already exists. Please enter a unique one.");
                                    continue;
                                }
                                if (!ISBN.matches("\\d+")){
                                    System.out.println("Only digits are allowed: ");
                                    continue;
                                }
                                break;
                            }

                            String Title;
                            while (true) {
                                System.out.print("Title: ");
                                Title = sc.nextLine();
                                if (Title.isBlank()) {
                                    System.out.println("Title cannot be empty.");
                                    continue;
                                }
                                if (Title.matches(".*\\d.*")){
                                    System.out.println("Title cannot contain any number: ");
                                    continue;
                                }

                                break;
                            }

                            String author;
                            while (true) {
                                System.out.print("Author: ");
                                author = sc.nextLine();
                                if (author.isBlank()) {
                                    System.out.println("Author cannot be empty.");
                                    continue;
                                }
                                if (author.matches(".*\\d.*")){
                                    System.out.println("Author cannot contain any number: ");
                                    continue;
                                }
                                break;
                            }

                            String volume;
                            while (true) {
                                System.out.print("Volume: ");
                                volume = sc.nextLine();
                                if (volume.isBlank()) {
                                    System.out.println("Volume cannot be empty.");
                                    continue;
                                }

                                if (!volume.matches("\\d+")){
                                    System.out.println("Only digits are allowed: ");
                                    continue;
                                }
                                break;
                            }

                            String publisher;
                            while (true) {
                                System.out.print("Publisher: ");
                                publisher = sc.nextLine();
                                if (publisher.isBlank()) {
                                    System.out.println("Publisher cannot be empty.");
                                    continue;
                                }
                                if (publisher.matches(".*\\d.*")){
                                    System.out.println("Author cannot contain any number: ");
                                    continue;
                                }
                                break;
                            }

                            String genre;
                            while (true) {
                                System.out.print("Genre: ");
                                genre = sc.nextLine();
                                if (genre.isBlank()) {
                                    System.out.println("Genre cannot be empty.");
                                    continue;
                                }
                                if (genre.matches(".*\\d.*")){
                                    System.out.println("genre cannot contain any number: ");
                                    continue;
                                }
                                break;
                            }

                            Boolean availability = true;

                            String Edition;
                            while (true) {
                                System.out.print("Edition: ");
                                Edition = sc.nextLine();
                                if (!Edition.isBlank()) break;
                                System.out.println("Edition cannot be empty.");
                            }


                            String confirmation;
                            while (true) {
                                System.out.print("Confirm insertion? (y/n): ");
                                confirmation = sc.nextLine().trim().toLowerCase();
                                if (confirmation.equals("y") || confirmation.equals("n")) break;
                                System.out.println("Please enter only 'y' or 'n'.");
                            }

                            if (confirmation.equals("y")) {
                                Book book = new Book(ISBN, Title, author, volume, publisher, genre, availability, Edition);
                                Transaction t = sess.beginTransaction();
                                sess.persist(book);
                                t.commit();
                                break;
                            } else {
                                System.out.println("Re-entering book details...\n");
                            }
                        }
                    }
                    break;

                case 2:
                    Book BookToRemove;
                    String ISBN;
                    String confirmation;
                    String check = "y";

                    while (true) {
                        System.out.print("ISBN: ");
                        ISBN = sc.nextLine().trim();
                        if (ISBN.isEmpty()) {
                            System.out.println("ISBN cannot be empty.");
                            continue;
                        }
                        if (!ISBN.matches("^[^-_\\s]+$")) {
                            System.out.println("Input must not contain hyphens (-), underscores (_), or spaces.");
                            continue;
                        }
                        if (sess.find(Book.class, ISBN) == null) {
                            System.out.println("ISBN is not present in the DB! ");
                            System.out.println("Want to try again? (y/n)");
                            while (true) {

                                check = sc.nextLine().trim().toLowerCase();
                                if (check.isEmpty()) {
                                    System.out.println("Input cannot be empty.");
                                    continue;
                                }
                                if (check.equals("y") || check.equals("n")) break;
                                System.out.println("U can enter only two options 'y'  or 'n'. ");
                            }
                            if (check.equals("n")) break;
                            continue;
                        }
                        break;
                    }
                    if (check.equals("y")) {

                        System.out.println("ISBN found in the DB.");
                        System.out.println("Are u sure u want to remove the book? (y/n)");
                        BookToRemove = sess.find(Book.class, ISBN);

                        while (true) {

                            confirmation = sc.nextLine().trim().toLowerCase();
                            if (confirmation.isEmpty()) {
                                System.out.println("Input cannot be empty.");
                                continue;
                            }
                            if (confirmation.equals("y") || confirmation.equals("n")) break;
                            System.out.println("U can enter only two options 'y'  or 'n'. ");


                        }

                        if (confirmation.equals("y")) {
                            Transaction t = sess.beginTransaction();
                            sess.remove(BookToRemove);
                            t.commit();
                            System.out.println("Book removed successfully! ");
                        } else {
                            System.out.println("Book is Not removed from Library.");
                        }
                    }
                    break;

                case 3:
                    int nOfMembers;
                    System.out.println("How many members do u want to insert? ");
                    while (true) {
                        try {
                            nOfMembers = Integer.parseInt(sc.nextLine());
                            if (nOfMembers <= 0) {
                                System.out.println("The number of members to insert should be positive. ");
                            }
                            if (nOfMembers >= 15) {
                                System.out.println("Adding so many member's at a time will cause trouble so please add 10 members every time at max! ");
                            }
                            if (nOfMembers > 0 && nOfMembers <= 15) {
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("does not match the Integer regular expression, or is out of range");
                        } catch (NumberFormatException e) {
                            System.out.println("Only numbers are allowed: ");
                        } catch (Exception e) {
                            System.out.println("Something went wrong! " + e);
                        }
                    }
                    for (int i = 0; i < nOfMembers; i++) {
                        while (true) {

                            String name;
                            while (true) {
                                System.out.print("Enter full Name: ");
                                name = sc.nextLine().trim().toLowerCase();

                                if (name.isEmpty()) {
                                    System.out.println("Name cannot be empty.");
                                    continue;
                                }

                                if (!name.matches("^[a-zA-Z ]+$")) {
                                    System.out.println("Input must not contain numbers or symbols.");
                                    continue;
                                }

                                if (name.length() > 40) {
                                    System.out.println("The name should be less than 40 characters including spaces.");
                                    continue;
                                }

                                TypedQuery<Member> query = sess.createQuery(
                                        "FROM Member WHERE name = :name", Member.class);
                                query.setParameter("name", name);

                                try {
                                    query.getSingleResult();
                                    System.out.println("Same name already exists.");
                                } catch (NoResultException e) {
                                    // No existing member found, exit loop
                                    break;
                                }
                            }


                            String phone;
                            while (true) {
                                System.out.print("Enter phone number: ");
                                phone = sc.nextLine().trim();

                                if (phone.isEmpty()) {
                                    System.out.println("Phone number cannot be empty.");
                                    continue;
                                }

                                // Allow digits, spaces, and hyphens only
                                if (!phone.matches("^[0-9\\- ]+$")) {
                                    System.out.println("Phone number can only contain digits, spaces, and hyphens (-).");
                                    continue;
                                }

                                // Extract digits only
                                String digitsOnly = phone.replaceAll("\\D", "");

                                if (digitsOnly.length() != 10) {
                                    System.out.println("Phone number must contain exactly 10 digits.");
                                    continue;
                                }

                                // Check for existing member with the same phone
                                try {
                                    TypedQuery<Member> query = sess.createQuery(
                                            "FROM Member WHERE phone = :phone", Member.class);
                                    query.setParameter("phone", digitsOnly);
                                    query.getSingleResult(); // If found, throw duplicate warning
                                    System.out.println("Phone number already exists in the database.");
                                } catch (NoResultException e) {
                                    // No existing member found — valid input
                                    phone = digitsOnly; // normalize the phone number to digits-only format
                                    break;
                                }
                            }


                            String email;
                            while (true) {
                                System.out.print("Enter email: ");
                                email = sc.nextLine().trim();

                                if (email.isEmpty()) {
                                    System.out.println("Email cannot be empty.");
                                    continue;
                                }

                                // Basic email validation regex
                                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                                    System.out.println("Invalid email format. Please enter a valid email address.");
                                    continue;
                                }

                                break;
                            }

                            String membershipType;
                            Set<String> allowedTypes = new HashSet<>(Arrays.asList(
                                    "adult", "child/teen", "senior citizen", "student", "staff"
                            ));
                            System.out.println(allowedTypes);
                            while (true) {

                                System.out.print("Enter membership type from above options: ");
                                membershipType = sc.nextLine().trim().toLowerCase();

                                if (!allowedTypes.contains(membershipType)) {
                                    System.out.println("Invalid membership type. Allowed: adult, child/teen, senior citizen, student, staff.");
                                    continue;
                                }
                                break;
                            }

                            LocalDate membershipStartDate = LocalDate.now();
                            String membershipDuration;
                            LocalDate membershipEndDate;
                            while (true) {
                                System.out.print("Enter membership duration (e.g., 1 week, 3 months, 1 year): ");
                                membershipDuration = sc.nextLine().trim().toLowerCase();


                                if (membershipDuration.isEmpty()) {
                                    System.out.println("membershipDuration cannot be empty.");
                                    continue;
                                }
                                String[] parts = membershipDuration.split(" ");
                                if (parts.length != 2) {
                                    System.out.println("Invalid format. Try again.");
                                    continue;
                                }

                                int amount;
                                try {
                                    amount = Integer.parseInt(parts[0]);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number. Try again.");
                                    continue;
                                }

                                String unit = parts[1];
                                switch (unit) {
                                    case "week", "weeks" -> {
                                        if (amount < 1 || amount > 4) {
                                            System.out.println("Weeks must be between 1 and 4. Try again.");
                                            continue;
                                        }
                                        membershipEndDate = membershipStartDate.plusWeeks(amount);
                                    }
                                    case "month", "months" -> {
                                        if (amount < 1 || amount > 6) {
                                            System.out.println("Months must be between 1 and 6. Try again.");
                                            continue;
                                        }
                                        membershipEndDate = membershipStartDate.plusMonths(amount);
                                    }
                                    case "year", "years" -> {
                                        if (amount < 1 || amount > 2) {
                                            System.out.println("Years must be between 1 and 2. Try again.");
                                            continue;
                                        }
                                        membershipEndDate = membershipStartDate.plusYears(amount);
                                    }
                                    default -> {
                                        System.out.println("Invalid unit. Use week(s), month(s), or year(s). Try again.");
                                        continue;
                                    }
                                }

                                break;
                            }

                            double pricePaid;
                            while (true) {
                                System.out.print("Enter price: ");
                                String input = sc.nextLine().trim();

                                if (input.isEmpty()) {
                                    System.out.println("Input cannot be empty!");
                                    continue;
                                }

                                try {
                                    pricePaid = Double.parseDouble(input);

                                    if (Double.isNaN(pricePaid)) {
                                        System.out.println("Input cannot be NaN!");
                                        continue;
                                    }

                                    if (Double.isInfinite(pricePaid)) {
                                        System.out.println("Input cannot be infinite!");
                                        continue;
                                    }

                                    if (pricePaid <= 0) {
                                        System.out.println("Price must be greater than 0!");
                                        continue;
                                    }
                                    break;

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number format! Please enter a valid number.");
                                }
                            }

                            String MemberConfirmation;
                            while (true) {
                                System.out.print("Confirm insertion? (y/n): ");
                                MemberConfirmation = sc.nextLine().trim().toLowerCase();
                                if (MemberConfirmation.equals("y") || MemberConfirmation.equals("n")) break;
                                System.out.println("Please enter only 'y' or 'n'.");
                            }
                            if (MemberConfirmation.equals("y")) {
                                ///  name inserted carefully in DB
                                name = name.toLowerCase();

                                ///  number inserted carefully in DB
                                String newPhone = phone.replaceAll("\\D", "");

                                Member m = new Member(name, email, newPhone, membershipType, membershipStartDate, membershipDuration, membershipEndDate, pricePaid);
                                Transaction t = sess.beginTransaction();
                                sess.persist(m);
                                t.commit();
                                break;
                            } else {
                                System.out.println("Re-entering Member details...\n");
                            }


                        }
                    }
                    break;

                case 4:

                    System.out.println("Search Member Details by Name");

                    String searchName;
                    while (true) {
                        System.out.print("Enter member name or part of it: ");
                        searchName = sc.nextLine().trim().toLowerCase();

                        if (searchName.isEmpty()) {
                            System.out.println("Name cannot be empty.");
                            continue;
                        }

                        if (!searchName.matches("^[a-zA-Z ]+$")) {
                            System.out.println("Name must not contain numbers or symbols.");
                            continue;
                        }

                        break;
                    }

                    TypedQuery<Member> query = sess.createQuery(
                            "FROM Member WHERE LOWER(name) LIKE :name", Member.class);
                    query.setParameter("name", "%" + searchName + "%");

                    List<Member> results = query.getResultList();

                    if (results.isEmpty()) {
                        System.out.println("❌ No member found matching the name: \"" + searchName + "\"");
                    } else {
                        System.out.println("\n✅ Members Found:\n");

                        System.out.printf("%-5s %-25s %-25s %-15s %-10s %-12s %-12s %-10s %-10s%n",
                                "ID", "Name", "Email", "Phone", "Type", "Start", "End", "Duration", "Price");

                        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

                        for (Member m : results) {
                            System.out.printf("%-5d %-25s %-25s %-15s %-10s %-12s %-12s %-10s %-10.2f%n",
                                    m.getM_ID(),
                                    m.getName(),
                                    m.getEmail(),
                                    m.getPhone(),
                                    m.getMembershipType(),
                                    m.getMembershipStartDate(),
                                    m.getMembershipEndDate(),
                                    m.getMembershipDuration(),
                                    m.getPricePaid());

                            // Show borrowed books, if any
                            List<Book> borrowed = m.getBorrowedBooks();
                            if (borrowed != null && !borrowed.isEmpty()) {
                                System.out.println("   📚 Borrowed Books:");
                                for (Book b : borrowed) {
                                    System.out.printf("   - %s by %s (ISBN: %s)%n",
                                            b.getTitle(), b.getAuthor(), b.getISBN());
                                }
                            } else {
                                System.out.println("   📘 No books currently borrowed.");
                            }

                            System.out.println();
                        }
                    }


                    break;

                case 5:
                    String removeName;
                    while (true) {
                        System.out.print("Name of member to remove: ");
                        removeName = sc.nextLine();
                        removeName = removeName.toLowerCase();
                        if (removeName.isBlank()) {
                            System.out.println("name cannot be empty.");
                            continue;
                        }
                        if (removeName.matches(".*\\d.*")){
                            System.out.println("name cannot contain any number: ");
                            continue;
                        }
                        break;
                    }
                    String removePhone;
                    while (true) {
                        System.out.print("Phone number of a member to remove: ");
                        removePhone = sc.nextLine().trim();

                        if (removePhone.isEmpty()) {
                            System.out.println("Phone number cannot be empty.");
                            continue;
                        }

                        if (!removePhone.matches("^[0-9\\- ]+$")) {
                            System.out.println("Phone number can only contain digits, spaces, and hyphens (-).");
                            continue;
                        }

                        String digitsOnly = removePhone.replaceAll("\\D", "");
                        if (digitsOnly.length() != 10) {
                            System.out.println("Phone number must contain exactly 10 digits.");
                            continue;
                        }
                        if (!removePhone.matches(".*\\d.*")){
                            System.out.println("phone cannot contain any characters: ");
                            continue;
                        }

                        break;
                    }

                    TypedQuery<Member> query2 = sess.createQuery(
                            "FROM Member WHERE name = :name AND phone = :phone", Member.class);
                    query2.setParameter("name", removeName);
                    query2.setParameter("phone", removePhone);

                    Member member2;
                    try {
                        member2 = query2.getSingleResult();
                        System.out.println();
                        System.out.println("Removed Member: ");
                        System.out.println(member2);
                        System.out.println();
                        Transaction t = sess.beginTransaction();
                        sess.remove(member2);
                        t.commit();

                    } catch (NoResultException e) {
                        System.out.println("No member found with given name and phone.");
                    }

                    break;

                case 6:
                    System.out.println("Successfully Exited from the Admin Login! ");
                    break;

                default:
                    System.out.println("Something went wrong! ");
                    break;

            }

        } while (option != 6);
    }
}
