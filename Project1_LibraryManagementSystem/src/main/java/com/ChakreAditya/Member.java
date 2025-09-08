package com.ChakreAditya;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int M_ID;
    private String name;
    private String email;
    private String phone;
    private String membershipType;
    private  LocalDate membershipStartDate;
    private  String membershipDuration;
    private  LocalDate membershipEndDate;
    private Double PricePaid;
    @OneToMany
    private List<Book> BorrowedBooks;

    public int getM_ID() {
        return M_ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public String getMembershipDuration() {
        return membershipDuration;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public Double getPricePaid() {
        return PricePaid;
    }

    public List<Book> getBorrowedBooks() {
        return BorrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        BorrowedBooks = borrowedBooks;
    }


    public Member(){}

    public Member(String name, String email, String phone, String membershipType, LocalDate membershipStartDate, String membershipDuration, LocalDate membershipEndDate, Double PricePaid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.membershipStartDate = membershipStartDate;
        this.membershipDuration = membershipDuration;
        this.membershipEndDate = membershipEndDate;
        this.PricePaid = PricePaid;
    }

    @Override
    public String toString() {
        return "Member{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", membershipType='" + membershipType + '\'' +
                ", membershipStartDate=" + membershipStartDate +
                ", membershipDuration='" + membershipDuration + '\'' +
                ", membershipEndDate=" + membershipEndDate +
                ", PricePaid=" + PricePaid +
                ", BorrowedBooks=" + BorrowedBooks +
                '}';
    }
}

