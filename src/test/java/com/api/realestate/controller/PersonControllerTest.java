package com.api.realestate.controller;

import com.api.realestate.config.SpringBootIntegrationTest;
import com.api.realestate.entity.dto.PersonDTO;
import com.api.realestate.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String V_1_PERSONS = "/v1/persons/";
    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 99L;

    @Test
    public void createPerson_withAdminRole() throws Exception {

        String dtoString = "{ " +
                "   \"name\":\"Lily Aldrin\", " +
                "   \"email\":\"lily.aldrin@email.com\", " +
                "   \"password\":\"password321\", " +
                "   \"personRole\":99 " +
                "}";
        PersonDTO expected = new ObjectMapper().readValue(dtoString, PersonDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST + port + V_1_PERSONS, expected, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Integer createdId = JsonPath.read(responseEntity.getBody(), "$.id");

        PersonDTO actual = new PersonDTO(personRepository.findById(Long.valueOf(createdId)).get());

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPersonRole(), actual.getPersonRole());

    }

    @Test
    public void findById_withInvalidId() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + V_1_PERSONS + INVALID_ID, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void b_findById_withValidId() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + V_1_PERSONS + VALID_ID, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "{" +
                "\"id\":1," +
                "\"name\":\"Ted Mosby\"," +
                "\"email\":\"mosby.architect@email.com\"," +
                "\"password\":\"$2a$10$vNT6MR0k3uGKaCZgQZq5JO8yX01SsWKzL1WDSNtsLrsWonsWBeo82\"," +
                "\"personRole\":1" +
                "}";
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    public void a_findAll() throws JSONException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + V_1_PERSONS, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "[" +
                "{\"id\":1,\"name\":\"Ted Mosby\",\"email\":\"mosby.architect@email.com\",\"password\":\"$2a$10$vNT6MR0k3uGKaCZgQZq5JO8yX01SsWKzL1WDSNtsLrsWonsWBeo82\",\"personRole\":1}," +
                "{\"id\":2,\"name\":\"Robin Scherbatsky\",\"email\":\"robin.sparkles@email.com.ca\",\"password\":\"$2a$10$3.WoOJ2Jq8z3UyOa5eZZduTpZX1EZGcqp53YojnRqrX60ZvHEkPS6\",\"personRole\":2}," +
                "{\"id\":3,\"name\":\"Barney Stinson\",\"email\":\"stinson@email.com\",\"password\":\"$2a$10$dTGz9/0Qk3I.5b4Q9U47WuXfAAXuyl2AEZzpcS0r.St7kNEoWR8HW\",\"personRole\":99}" +
                "]";

        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void editName() throws Exception {
        String dtoString = "{" +
                "\"id\":1," +
                "\"name\":\"Ted Evelyn Mosby\"," +
                "\"email\":\"mosby.architect@email.com\"," +
                "\"password\":\"$2a$10$vNT6MR0k3uGKaCZgQZq5JO8yX01SsWKzL1WDSNtsLrsWonsWBeo82\"," +
                "\"personRole\":1" +
                "}";
        PersonDTO dto = new ObjectMapper().readValue(dtoString, PersonDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange(HTTP_LOCALHOST + port + V_1_PERSONS + VALID_ID, HttpMethod.PUT, new HttpEntity<>(dto),
                String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String actual = new ObjectMapper().writeValueAsString(new PersonDTO(personRepository.findById(VALID_ID).get()));
        String expected = "{" +
                "\"id\":1," +
                "\"name\":\"Ted Evelyn Mosby\"," +
                "\"email\":\"mosby.architect@email.com\"," +
                "\"password\":\"$2a$10$vNT6MR0k3uGKaCZgQZq5JO8yX01SsWKzL1WDSNtsLrsWonsWBeo82\"," +
                "\"personRole\":1" +
                "}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void z_deleteById() throws Exception {
        final ResponseEntity<String> responseEntity = restTemplate.exchange(HTTP_LOCALHOST + port + V_1_PERSONS + VALID_ID, HttpMethod.DELETE, null,
                String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Integer deleted = jdbcTemplate.queryForObject("SELECT DELETED FROM PERSON WHERE id = " + VALID_ID, Integer.class);
        Assert.assertEquals(VALID_ID, deleted, 0);
    }

    @Test
    public void z_deleteById_withInvalidId() throws Exception {
        final ResponseEntity<String> responseEntity = restTemplate.exchange(HTTP_LOCALHOST + port + V_1_PERSONS + INVALID_ID, HttpMethod.DELETE, null,
                String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
