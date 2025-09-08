package com.ChakreAditya;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private  String ISBN;
    private  String Title;
    private  String author;
    private  String volume;
    private  String publisher;
    private  String genre;
    private  Boolean availability;
    private  String Edition;

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return author;
    }

    public String getVolume() {
        return volume;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public String getEdition() {
        return Edition;
    }

    public Book() {
        // required by Hibernate
    }

    public Book(String ISBN, String title, String author, String volume, String publisher, String genre, Boolean availability, String edition) {
        this.ISBN = ISBN;
        Title = title;
        this.author = author;
        this.volume = volume;
        this.publisher = publisher;
        this.genre = genre;
        this.availability = availability;
        Edition = edition;
    }


}
