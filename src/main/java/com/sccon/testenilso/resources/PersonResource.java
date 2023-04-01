package com.sccon.testenilso.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sccon.testenilso.DTOs.PersonUpdateDTO;
import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.enums.AgeOutput;
import com.sccon.testenilso.enums.SalaryOutput;
import com.sccon.testenilso.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonResource {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> users = personService.findAllOrderedByName();

        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        var person = personService.findById(id);

        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        personService.save(person);

        return ResponseEntity.ok(person);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Person> delete(@PathVariable Long id)
    {
        personService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody PersonUpdateDTO personUpdateDTO)
    {
        Person updatedPerson;

        updatedPerson = personService.update(id, personUpdateDTO);

        return ResponseEntity.ok(updatedPerson);
    }

    @GetMapping("{id}/age")
    public ResponseEntity<String> getAge(@PathVariable Long id, @RequestParam String output) {
        AgeOutput enumValue;

        try {
            enumValue = AgeOutput.valueOf(output.toUpperCase());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        var person = personService.findById(id);

        Long result = 0L;

        switch (enumValue) {
            case DAYS -> result = ChronoUnit.DAYS.between(person.getBirthDate(), LocalDate.now());
            case MONTHS -> result = ChronoUnit.MONTHS.between(person.getBirthDate(), LocalDate.now());
            case YEARS -> result = ChronoUnit.YEARS.between(person.getBirthDate(), LocalDate.now());
        }

        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("{id}/salary")
    public ResponseEntity<String> getSalary(@PathVariable Long id, @RequestParam String output) {
        SalaryOutput enumValue;

        try {
            enumValue = SalaryOutput.valueOf(output.toUpperCase());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        var person = personService.findById(id);

        Double result = 0.00;

        switch (enumValue) {
            case MIN -> result = person.getSalary() / 1302.00;
            case FULL -> result = person.getSalary();
        }

        return ResponseEntity.ok(String.format("%,.2f", result));
    }

    @PatchMapping(value = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Person> patch(@PathVariable Long id, @RequestBody JsonPatch patch) {
        Person patchedPerson;

        try {
            var person = personService.findById(id);

            patchedPerson = applyPatchToCustomer(patch, person);

            personService.update(id, patchedPerson);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(patchedPerson);
    }

    private Person applyPatchToCustomer(JsonPatch patch, Person targetPerson)
            throws JsonPatchException, JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode patched = patch.apply(objectMapper.convertValue(targetPerson, JsonNode.class));

        return objectMapper.treeToValue(patched, Person.class);
    }
}
