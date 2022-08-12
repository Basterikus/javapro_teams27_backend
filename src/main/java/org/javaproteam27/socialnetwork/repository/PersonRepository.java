package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    public int save(Person person) {
        return jdbcTemplate.update("INSERT INTO person(id, first_name, last_name, reg_date, email, password) " +
                "values(?, ?, ?, ?, ?, ?)", person.getId(), person.getFirstName(), person.getLastName(),
                person.getRegDate(), person.getEmail(), person.getPassword());
    }
}
