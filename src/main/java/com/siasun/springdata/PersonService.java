package com.siasun.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hliu on 2016/11/2.
 */

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void updatePersonEmail(String email,Integer id) {
        personRepository.updatePersonEmail(id,email);
    }

    @Transactional
    public void savePersons(List<Person> persons) {
        personRepository.save(persons);
    }
}
