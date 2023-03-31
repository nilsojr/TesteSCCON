package com.sccon.testenilso.resources;

import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonResource {

    @Autowired
    PersonService personService;

    //Disponibilize um endpoint (método GET - /person) que retorne a lista de pessoas,
    //ordenada por nome em ordem alfabética, com todos os atributos no formato JSON

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        List<Person> users = personService.findAllOrderedByName();
        return ResponseEntity.ok(users);
    }
}
