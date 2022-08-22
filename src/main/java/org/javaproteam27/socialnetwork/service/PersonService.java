package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    
    private final PersonRepository personRepository;
    
    
    public Person findById(int id) {
        return personRepository.findById(id);
    }
    
    public Person getPersonById(Integer id){
        return personRepository.getPersonById(id);
    }
    
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
    
    public Integer count() {
        return personRepository.count();
    }
    
}
