package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

//@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
//@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
//@Sql(value = "/sql/person/insert-person.sql", executionPhase = BEFORE_TEST_METHOD)
public class PersonRepositoryTest {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Test
    void save_shouldSavePerson_whenFieldsCorrectAndNotExists() {
        int preSaveCount = 0;
        int expectedCount = 1;
        Person person = getPerson();
    
        personRepository.save(person);
        int postSaveCount = personRepository.count();
        int actualCount = postSaveCount - preSaveCount;
        
        assertEquals(expectedCount, actualCount);
    }
    
    private Person getPerson() {
        Person person = new Person();
    
        person.setId(1);
        person.setFirstName("Alex");
        person.setLastName("Fred");
        person.setRegDate(LocalDateTime.of(2007, 7, 12, 12, 12));
        person.setBirthDate(LocalDateTime.of(2010, 7, 12, 12, 12));
        person.setEmail("qwerty@mail.ru");
        person.setPhone("89999999999");
        person.setPassword("123456");
        person.setPhoto("http://www.photo.com");
        person.setAbout("about");
        person.setCityId(null);
        person.setConfirmationCode(123456);
        person.setIsApproved(true);
        person.setMessagesPermission("string");
        person.setLastOnlineTime(LocalDateTime.of(2012, 7, 12, 12, 12));
        person.setIsBlocked(false);
        
        return person;
    }
    
}
