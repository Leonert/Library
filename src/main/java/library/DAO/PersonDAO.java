package library.DAO;

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

    public void editPerson(int id, String FIO, int yearOfBirth) {
        jdbcTemplate.update("update person set FIO=?,yearofbirth=? where id=?", FIO, yearOfBirth, id);
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
}
