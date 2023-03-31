package com.sccon.testenilso.services;

import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAllOrderedByName() {
        return personRepository.findAllByOrderByNameAsc();
    }
}
