package library.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "fio")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "ФИО должно быть в формате Фамилия Имя Отчество")
    private String FIO;
    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Должно быть больше 1900")
    @Max(value = 2023, message = "Должно быть меньше 2023")
    private int yearOfBirth;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @OneToMany(mappedBy = "reader")
    private List<Book> books;

    public String getFIO() {
        return FIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
