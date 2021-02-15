package com.api.realestate.controller;

import com.api.realestate.config.SpringBootIntegrationTest;
import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
public class PersonControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final Integer VALID_ID = 1;
    public static final Integer INVALID_ID = 99;

    @Test
    public void createPerson_withAdminRole() throws Exception {

        String dtoString = "{ " +
                "   \"name\":\"Lily Aldrin\", " +
                "   \"email\":\"lily.aldrin@email.com\", " +
                "   \"password\":\"password321\", " +
                "   \"personRole\":99 " +
                "}";
        PersonDTO expected = new ObjectMapper().readValue(dtoString, PersonDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST + port + "/v1/persons", expected, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Integer createdId = JsonPath.read(responseEntity.getBody(), "$.id");

        PersonDTO actual = new PersonDTO(personRepository.findById(Long.valueOf(createdId)).get());

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPersonRole(), actual.getPersonRole());

    }

    @Test
    public void findById_withInvalidId() throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + "/v1/persons/" + INVALID_ID, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}
