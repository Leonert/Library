package library.controllers;

import library.DAO.BookDAO;
import library.DAO.PersonDAO;
import library.models.Book;
import library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id).orElse(new Book()));
        model.addAttribute("person", bookDAO.takenBy(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("emptyPerson", new Person());
        return "book/show";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("book", new Book());
        return "book/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "book/add";
        bookDAO.addBook(book);
        return "redirect:/book";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.show(id);
        if(book.isEmpty()) return "redirect:/book";
        model.addAttribute("book", book.get());
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("book") Book book) {
        bookDAO.editBook(book);
        return "redirect:/book";
    }

    @PostMapping("/release/{id}")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/book";
    }

    @PostMapping("/issue/{id}")
    public String issueBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.issueBook(id, person.getId());
        return "redirect:/book";
    }
}
