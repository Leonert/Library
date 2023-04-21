package library.controllers;

import library.models.Book;
import library.models.Person;
import library.services.BookService;
import library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping("")
    public String index(Model model, @RequestParam(required = false) Optional<Integer> page,
                        @RequestParam(required = false) Optional<Integer> books_per_page, @RequestParam(required = false) Optional<String> sortBy) {
        Page<Book> result = bookService.findAll(page.orElse(1), books_per_page.orElse(10), sortBy.orElse("id"));
        model.addAttribute("books", result);
        int totalPages = result.getTotalPages();

        if(totalPages > 0) model.addAttribute("pageNumbers", IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()));

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id).orElse(new Book());
        model.addAttribute("book", book);
        model.addAttribute("person", book.getReader());
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("emptyPerson", new Person());
        return "book/show";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("book", new Book());
        return "book/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "book/add";
        bookService.save(book);
        return "redirect:/book";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookService.findOne(id);
        if(!book.isPresent()) return "redirect:book";
        model.addAttribute("book", book.get());
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) return "book/edit";
        bookService.update(id, book);
        return "redirect:/book";
    }

    @PostMapping("/release/{id}")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/book";
    }

    @PostMapping("/issue/{id}")
    public String issueBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.issueBook(id, person);
        return "redirect:/book";
    }
}
