package com.sccon.testenilso.config;

import com.sccon.testenilso.entities.Person;
import com.sccon.testenilso.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        Person person1 = new Person(null, "Nilso", LocalDate.of(1989, 1, 1), LocalDate.now(), 3259.36);
        Person person2 = new Person(null, "Jose", LocalDate.now(), LocalDate.now(), 1302.00);
        Person person3 = new Person(null, "Baseggio", LocalDate.now(), LocalDate.now(), 2604.00);

        personRepository.saveAll(Arrays.asList(person1, person2, person3));
    }

}
