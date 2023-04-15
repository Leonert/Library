package library.DAO;

import library.models.Book;
import library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addPerson(String FIO, int yearOfBirth) {
        jdbcTemplate.update("insert into person (fio, yearofbirth) values (?, ?)", FIO, yearOfBirth);
    }

    public void addPerson(Person person) {
        addPerson(person.getFIO(), person.getYearOfBirth());
    }

    public void editPerson(int id, String FIO, int yearOfBirth) {
        jdbcTemplate.update("update person set FIO=?,yearofbirth=? where id=?", FIO, yearOfBirth, id);
    }

    public void editPerson(Person person) {
        editPerson(person.getId(), person.getFIO(), person.getYearOfBirth());
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> takenBooks(int id) {
        return jdbcTemplate.query("select id, title, author, year from taken_books join book b on b.id = taken_books.book_id where person_id = ?;", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean isFIOUnique(String FIO) {
        return jdbcTemplate.query("select * from person where fio = ?", new Object[]{FIO}, new BeanPropertyRowMapper<>(Person.class)).isEmpty();
    }
}
