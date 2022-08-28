package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.PersonMapper;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final RowMapper<Person> rowMapper = new PersonMapper();
    private final JdbcTemplate jdbcTemplate;


    public void save(Person person) {

        String sql = "insert into person(first_name, last_name, reg_date, birth_date, email, phone, " +
                "password, photo, about, city_id, confirmation_code, is_approved, messages_permission, " +
                "last_online_time, is_blocked) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getRegDate(),
                person.getBirthDate(), person.getEmail(), person.getPhone(), person.getPassword(),
                person.getPhoto(), person.getAbout(), person.getCityId(), person.getConfirmationCode(),
                person.getIsApproved(), person.getMessagesPermission(), person.getLastOnlineTime(),
                person.getIsBlocked());
    }

    public Person findById(int id) {
        try {
            String sql = "select * from person where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("person id = " + id);
        }
    }

    public Person findByEmail(String email) {
        try {
            String sql = "select * from person where email like ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("person email = " + email);
        }
    }

    public Integer count() {
        try {
            String sql = "select count(*) from person";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0; // todo обработать исключение
        }
    }
    public Person getPersonById(Integer id){
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = " + id, Person.class);
    }
}