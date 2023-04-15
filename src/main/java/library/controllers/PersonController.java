package library.controllers;

import library.DAO.PersonDAO;
import library.models.Person;
import library.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "/person/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id).orElse(new Person()));
        model.addAttribute("books", personDAO.takenBooks(id));
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
        personDAO.addPerson(person);
        return "redirect:/person";
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/person";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personDAO.show(id);
        if(person.isEmpty()) return "redirect:person";
        model.addAttribute("person", person.get());
        return "/person/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (!personDAO.isFIOUnique(person.getFIO())) bindingResult.rejectValue("FIO", "exists", "such user already exists");
        if(bindingResult.hasErrors()) return "/person/edit";
        personDAO.editPerson(person);
        return "redirect:/person";
    }
}
