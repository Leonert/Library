package library.controllers;

import library.models.Person;
import library.services.BookService;
import library.services.PeopleService;
import library.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PeopleService peopleService, BookService bookService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "/person/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "/person/show";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("person", new Person());
        return "/person/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "person/add";
        peopleService.save(person);
        return "redirect:/person";
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/person";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findOne(id);
        if(person.getFIO() == null) return "redirect:person";
        model.addAttribute("person", person);
        return "/person/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
//        if (!personDAO.isFIOUnique(person.getFIO())) bindingResult.rejectValue("FIO", "exists", "such user already exists");
        if(bindingResult.hasErrors()) return "/person/edit";
        peopleService.update(id, person);
        return "redirect:/person";
    }
}
