package com.Leonert.spring.Library.services;

import com.Leonert.spring.Library.models.Book;
import com.Leonert.spring.Library.models.Person;
import com.Leonert.spring.Library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElseGet(Person::new);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();

            for (Book book: books) {
                book.setBookDelayed(((new Date()).getTime() - book.getDateOfIssue().getTime())/86400000 >= 10);
            }

            return books;
        }
        else return Collections.emptyList();
    }


}
