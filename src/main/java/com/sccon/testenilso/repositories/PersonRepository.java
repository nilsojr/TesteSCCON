package com.sccon.testenilso.repositories;

import com.sccon.testenilso.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    public List<Person> findAllByOrderByNameAsc();
}
