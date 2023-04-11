package library.controllers;

import library.DAO.PersonDAO;
import library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "person/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id).orElse(new Person()));
        return "person/show";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("person") Person person, BindingResult bindingResult) {
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
        if(person.isEmpty()) return "redirect:/person";
        model.addAttribute("person", person.get());
        return "person/edit";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("person") Person person) {
        personDAO.editPerson(person);
        return "redirect:/person";
    }
}
