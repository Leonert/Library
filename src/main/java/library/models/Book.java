package library.models;

import javax.validation.constraints.*;

public class Book {
    private int id;

    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 2, max = 50, message = "Название должно содержать от 2 до 50 символов")
    private String title;
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+", message = "Имя автора должно быть в формате Имя Фамилия")
    private String author;
    @Min(value = 1500, message = "Год должен быть не меньше 1500")
    @Max(value = 2023, message = "Год должен быть не больше 2023")
    private int year;
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
