package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.PersonMapper;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final RowMapper<Person> rowMapper;
    private final JdbcTemplate jdbcTemplate;


    public void save(Person person) {

        String sql = "insert into person(first_name, last_name, reg_date, birth_date, email, phone, " +
                "password, photo, about, city_id, confirmation_code, is_approved, messages_permission, " +
                "last_online_time, is_blocked) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getRegDate(),
                person.getBirthDate(), person.getEmail(), person.getPhone(), person.getPassword(),
                person.getPhoto(), person.getAbout(), person.getCity(), person.getConfirmationCode(),
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

    public List<Person> getFriendsPersonById(Integer id){
        try {
            String sql = "SELECT * FROM friendship f\n" +
                    "join friendship_status fs on fs.id=f.status_id\n" +
                    "join person p on f.dst_person_id=p.id\n" +
                    "where fs.code = 'FRIEND' and src_person_id = ?";
            return jdbcTemplate.query(sql, rowMapper, id);
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

    public List<Person> findPeople (Person authorizedPerson, String firstName, String lastName, Integer ageFrom,
                                    Integer ageTo, String city, String country) {

        //TODO: JOOQ + Filter blocked people

        ArrayList<String> queryParts = new ArrayList<>();

        if(firstName != null) {
            queryParts.add("first_name = '" + firstName + "'");
        }

        if(lastName != null) {
            queryParts.add("last_name = '" + lastName + "'");
        }

        if(ageFrom != null) {
            queryParts.add("date_part('year', age(birth_date))::int > " + ageFrom);
        }

        if(ageTo != null) {
            queryParts.add("date_part('year', age(birth_date))::int < " + ageTo);
        }

        if(city != null) {
            queryParts.add("city = '" + city + "'");
        }

        if(country != null) {
            queryParts.add("country = '" + country + "'");
        }

        String buildQuery = "SELECT * FROM person WHERE " + String.join(" AND ", queryParts) + ";";
        return jdbcTemplate.query(buildQuery, rowMapper);
    }
}
