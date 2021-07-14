package com.api.realestate.service;

import com.api.realestate.entity.Person;
import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.entity.mapper.PersonMapper;
import com.api.realestate.repository.PersonRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PersonRepository personRepository;

    public HomeService(BCryptPasswordEncoder bCryptPasswordEncoder, PersonRepository personRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personRepository = personRepository;
    }

    public Long create(PersonDTO personDTO) {
        personDTO.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        Person person = PersonMapper.marshall(personDTO);
        return personRepository.save(person).getId();
    }

    public Person findById(Long id) throws NotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
    }

    public List<Person> findAll() {
        List<Person> personList = personRepository.findAll();
        return personList;
    }

    public void update(PersonDTO personDTO) throws NotFoundException {
        Person person = personRepository.findById(personDTO.getId()).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + personDTO.getId() + ">"));
        verifyIfPasswordChanged(personDTO, person.getPassword());
        personRepository.save(PersonMapper.marshall(personDTO));
    }

    private void verifyIfPasswordChanged(PersonDTO personDTO, String password) {
        if (!personDTO.getPassword().equals(password)) {
            personDTO.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        }
    }

    public void delete(Long id) throws NotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Couldn't load index set with ID <" + id + ">"));
        personRepository.delete(person);
    }
}
