package com.sccon.testenilso.services;

import com.sccon.testenilso.DTOs.PersonUpdateDTO;
import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.repositories.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAllOrderedByName() {
        return personRepository.findAllByOrderByNameAsc();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Boolean save(Person person) {
        if (person.getId() != null && personRepository.existsById(person.getId()))
            throw new EntityExistsException();

        personRepository.save(person);

        return true;
    }

    public Boolean deleteById(Long id) {
        if (!personRepository.existsById(id))
            throw new EntityNotFoundException();

        personRepository.deleteById(id);

        return true;
    }

    public Person update(Long id, Person person) {
        personRepository.save(person);

        return person;
    }

    public Person update(Long id, PersonUpdateDTO personUpdateDTO) {
        var person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        ModelMapper mapper = new ModelMapper();

        mapper.map(personUpdateDTO, person);

        personRepository.save(person);

        return person;
    }
}
