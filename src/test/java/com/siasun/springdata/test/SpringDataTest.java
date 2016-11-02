package com.siasun.springdata.test;

import com.siasun.springdata.Person;
import com.siasun.springdata.PersonRepository;
import com.siasun.springdata.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.*;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hliu on 2016/10/8.
 */
public class SpringDataTest {

    private ApplicationContext applicationContext;
    private PersonRepository personRepository;
    private PersonService personService;
    {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        personRepository = applicationContext.getBean(PersonRepository.class);
        personService = applicationContext.getBean(PersonService.class);
    }

    @Test
    public void testJDBC() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testJPA() {

    }

    @Test
    public void testKeyWord() {
//        Person person = personRepository.getByLastName("liuhao");
//        System.out.println(person);

//        List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("A",3);
//        List<Person> persons = personRepository.getByLastNameEndingWithAndIdLessThan("A",4);

//        System.out.println(persons);
    }

    @Test
    public void testQuery() {
//        Person person = personRepository.getMaxIdPerson();
//        System.out.println(person.getId());

//        List<Person> persons = personRepository.testQueryAnnotationParams2("AA257@163.com","AA");
//        System.out.println(persons);

//        long count = personRepository.getTotalCount();
//        System.out.println(count);

//        不行必须的需要事务
//        personRepository.updatePersonEmail(1,"AA257@163.com");
//        personService.updatePersonEmail("liuhao257@163.com",1);
    }

    @Test
    public void testCRUDRepository() {
        List<Person> persons = new ArrayList<Person>();

        for(int i = 'a';i <='z';i++) {
            Person person = new Person();
            person.setAddressId(i+1);
            person.setBirth(new Date());
            person.setEmail((char)i + ""+(char)i + "@257.com");
            person.setLastName((char)i + ""+(char)i);

            persons.add(person);
        }

        personService.savePersons(persons);
    }

    @Test
    public void testPagingAndSortingRespository() {
        //pageNo从 0 开始
        int pageNo = 3 - 1;
        int pageSize = 5;

        //Pageable 接口通常使用的其 PageRequest 实现类. 其中封装了需要分页的信息
        //排序相关的. Sort 封装了排序的信息
        //Order 是具体针对于某一个属性进行升序还是降序
        Order order1 = new Order(Sort.Direction.DESC,"id");
        Order order2 = new Order(Sort.Direction.ASC,"email");
        Sort sort = new Sort(order1,order2);

        PageRequest pageable = new PageRequest(pageNo,pageSize,sort);

        Page<Person> page = personRepository.findAll(pageable);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());
    }

    @Test
    public void testJpaRepository() {
        Person person = new Person();
        person.setBirth(new Date());
        person.setEmail("XYZ@257.com");
        person.setLastName("xyz");

        personRepository.saveAndFlush(person);
    }


    /**
     * 目标: 实现带查询条件的分页. id > 5 的条件
     *
     * 调用 JpaSpecificationExecutor 的 Page<T> findAll(Specification<T> spec, Pageable pageable);
     * Specification: 封装了 JPA Criteria 查询的查询条件
     * Pageable: 封装了请求分页的信息: 例如 pageNo, pageSize, Sort
     */
    @Test
    public void testJpaSpecificationExecutor() {
        int pageNo = 3 - 1;
        int pageSize = 5;
        PageRequest pageable = new PageRequest(pageNo, pageSize);

        //通常使用 Specification 的匿名内部类
        Specification<Person> specification = new Specification<Person>() {

            /**
             * @param *root: 代表查询的实体类.
             * @param query: 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
             * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
             * @param *cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
             * @return: *Predicate 类型, 代表一个查询条件.
             */

            @Override
            public Predicate toPredicate(Root<Person> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path path = root.get("id");
                Predicate predicate = cb.gt(path, 58);
                return predicate;
            }
        };

        Page<Person> page = personRepository.findAll(specification, pageable);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());
    }

    @Test
    public void testCustomRepositoryMethod(){
        personRepository.test();
    }
}
