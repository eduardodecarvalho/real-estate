package com.api.realestate.entity.mapper;

import com.api.realestate.entity.Person;
import com.api.realestate.entity.dto.PersonDTO;

public class PersonMapper {

    public static Person marshall(PersonDTO dto) {
        return new Person(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getPersonRole());
    }

    public static PersonDTO unmarshall(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getPassword());
    }
}
