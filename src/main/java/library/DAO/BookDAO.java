package library.DAO;

import library.models.Book;
import library.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addBook(Book book) {
        jdbcTemplate.update("insert into book (title, author, year) values (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void editBook(int id, String title, String author, int year) {
        jdbcTemplate.update("update book set title=?, author=?, year=? where id=?", title, author, year, id);
    }

    public void editBook(Book book) {
        editBook(book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> show(int id) {
        return jdbcTemplate.query("select * from book where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Optional<Person> takenBy(int id) {
        return jdbcTemplate.query("select id, fio, yearofbirth from person join taken_books tb on person.id = tb.person_id where book_id=?;", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("delete from taken_books where book_id = ?;", id);
    }

    public void issueBook(int bookId, int personId) {
        jdbcTemplate.update("insert into taken_books values (?, ?)", personId, bookId);
    }
}
