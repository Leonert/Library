package com.Leonert.spring.Library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно содержать от 2 до 50 символов")
    private String title;

    @Column(name = "author")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Имя автора должно быть в формате Имя Фамилия")
    private String author;
    @Column(name = "year")
    @Min(value = 1500, message = "Год должен быть не меньше 1500")
    @Max(value = 2023, message = "Год должен быть не больше 2023")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person reader;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Transient
    private boolean isBookDelayed;

    public void setBookDelayed(boolean bookDelayed) {
        isBookDelayed = bookDelayed;
    }

    public boolean isBookDelayed() {
        return isBookDelayed;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Person getReader() {
        return reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
