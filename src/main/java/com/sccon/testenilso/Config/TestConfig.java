package com.sccon.testenilso.Config;

import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        Person person1 = new Person(null, "Nilso", LocalDate.now(), LocalDate.now());
        Person person2 = new Person(null, "Jose", LocalDate.now(), LocalDate.now());
        Person person3 = new Person(null, "Baseggio", LocalDate.now(), LocalDate.now());

        personRepository.saveAll(Arrays.asList(person1, person2, person3));
    }

}
