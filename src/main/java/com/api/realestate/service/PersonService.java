package com.api.realestate.service;

import com.api.realestate.entity.Person;
import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.entity.mapper.PersonMapper;
import com.api.realestate.repository.PersonRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersonService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PersonRepository personRepository;

    public void create(PersonDTO personDTO) {
        personDTO.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        Person person = PersonMapper.marshall(personDTO);
        personRepository.save(person);
    }

    public PersonDTO findById(Long id) throws NotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
        return PersonMapper.unmarshall(person);
    }

    public List<PersonDTO> findAll() {
        List<Person> personList = personRepository.findAll();
        return personList.stream().map(PersonMapper::unmarshall).collect(Collectors.toList());
    }

    public void update(PersonDTO personDTO) {
        personDTO.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        Person person = PersonMapper.marshall(personDTO);
        personRepository.save(person);
    }

    public void delete(Long id) throws NotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
        personRepository.delete(person);
    }
}
