package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final RowMapper<Person> rowMapper = (rs, rowNum) -> {

        Person person = new Person();

        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setRegDate(rs.getTimestamp("reg_date").toLocalDateTime());
        person.setBirthDate(rs.getTimestamp("birth_date").toLocalDateTime());
        person.setEmail(rs.getString("email"));
        person.setPhone(rs.getString("phone"));
        person.setPassword(rs.getString("password"));
        person.setPhoto(rs.getString("photo"));
        person.setAbout(rs.getString("about"));
        person.setCityId(rs.getInt("city_id"));
        person.setConfirmationCode(rs.getInt("confirmation_code"));
        person.setIsApproved(rs.getBoolean("is_approved"));
        person.setMessagesPermission(MessagesPermission.valueOf(rs.getString("messages_permission")));
        person.setLastOnlineTime(rs.getTimestamp("last_online_time").toLocalDateTime());
        person.setIsBlocked(rs.getBoolean("is_blocked"));

        return person;
    };

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
            throw new EntityNotFoundException("person_id = " + id);
        }
    }

    public Person findByEmail(String email) {
        try {
            String sql = "select * from person where email like ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("person_email = " + email);
        }
    }

    public Integer count() {
        try {
            String sql = "select count(*) from person";
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
    public Person getPersonById(Integer id){
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = " + id, Person.class);
    }
}