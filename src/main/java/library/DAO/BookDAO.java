package library.DAO;

import library.models.Book;
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
}
