package com.api.realestate.controller;

import com.api.realestate.entity.dto.IdDTO;
import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.service.PersonService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/persons")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) throws NotFoundException {
        return new PersonDTO(personService.findById(id));
    }

    @GetMapping("")
    public List<PersonDTO> findPersons() {
        return personService.findAll().stream()
                .map(PersonDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PersonDTO personDTO) throws NotFoundException {
        personDTO.setId(id);
        personService.update(personDTO);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO create(@RequestBody PersonDTO personDTO) {
        return new IdDTO(personService.create(personDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        personService.delete(id);
    }
    
}
