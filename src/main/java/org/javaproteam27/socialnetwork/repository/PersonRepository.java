package org.javaproteam27.socialnetwork.repository;

import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {
    public Person getPersonById(Integer id){
        return new Person();
    }
}
