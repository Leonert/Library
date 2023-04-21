package library.services;

import library.models.Book;
import library.models.Person;
import library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(Integer page, Integer books_per_page, String sortBy) {
        return bookRepository.findAll(PageRequest.of(page - 1, books_per_page, Sort.by(sortBy)));
    }

    public Optional<Book> findOne(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> value.setReader(null));
    }

    @Transactional
    public void issueBook(int id, Person person) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> {
            value.setReader(person);
            value.setDateOfIssue(new Date());
        });
    }

    public List<Book> findBook(String query) {
        return bookRepository.findByTitleContaining(query);
    }
}