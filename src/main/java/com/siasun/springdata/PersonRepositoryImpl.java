package com.siasun.springdata;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by hliu on 2016/11/2.
 */
public class PersonRepositoryImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void test() {
        Person person = entityManager.find(Person.class,68);
        System.out.println(person.getLastName());
    }
}
