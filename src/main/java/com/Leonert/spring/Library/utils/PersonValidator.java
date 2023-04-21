package com.Leonert.spring.Library.utils;

import com.Leonert.spring.Library.models.Person;
import com.Leonert.spring.Library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Person person = (Person) o;
//        if (!personDAO.isFIOUnique(person.getFIO())) errors.rejectValue("FIO", "", "Человек с таким ФИО уже существует");
    }
}