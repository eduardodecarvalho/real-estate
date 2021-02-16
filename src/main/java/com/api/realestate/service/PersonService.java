package com.api.realestate.service;

import com.api.realestate.entity.Person;
import com.api.realestate.repository.PersonRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PersonRepository personRepository;

    public PersonService(BCryptPasswordEncoder bCryptPasswordEncoder, PersonRepository personRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personRepository = personRepository;
    }

    public Long create(Person person) {
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return personRepository.save(person).getId();
    }

    public Person findById(Long id) throws NotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
    }

    public List<Person> findAll() {
        List<Person> personList = personRepository.findAll();
        return personList;
    }

    public void update(Person person) throws NotFoundException {
        Person personFromDb = personRepository.findById(person.getId()).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + person.getId() + ">"));
        verifyIfPasswordChanged(person, person.getPassword());
        personRepository.save(person);
    }

    private void verifyIfPasswordChanged(Person person, String password) {
        if (!person.getPassword().equals(password)) {
            person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        }
    }

    public void delete(Long id) throws NotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
        personRepository.delete(person);
    }
}
