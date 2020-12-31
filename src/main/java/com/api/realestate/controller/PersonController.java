package com.api.realestate.controller;

import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/v1/persons/{id}")
    public PersonDTO findById(@PathVariable Long id) throws NotFoundException {
        return new PersonDTO(personService.findById(id));
    }

    @GetMapping("/v1/persons")
    public List<PersonDTO> findPersons() {
        return personService.findAll().stream()
                .map(PersonDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/v1/persons/{id}")
    public void update(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        personDTO.setId(id);
        personService.update(personDTO);
    }

    @PostMapping("/v1/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PersonDTO personDTO) {
        personService.create(personDTO);
    }

    @DeleteMapping("/v1/persons/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        personService.delete(id);
    }
    
}
