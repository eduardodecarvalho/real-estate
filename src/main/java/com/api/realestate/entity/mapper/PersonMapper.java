package com.api.realestate.entity.mapper;

import com.api.realestate.entity.Person;
import com.api.realestate.entity.dto.PersonDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public static Person marshall(PersonDTO dto) {
        return new Person(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getPersonRole());
    }

    public static PersonDTO unmarshall(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getPassword(), person.getPersonRole());
    }

    public static List<Person> marshall(List<PersonDTO> dtos) {
        return dtos.stream()
                .map(d -> marshall(d))
                .collect(Collectors.toList());
    }

    public static List<PersonDTO> unmarshall(List<Person> persons) {
        return persons.stream()
                .map(p -> unmarshall(p))
                .collect(Collectors.toList());
    }
}
