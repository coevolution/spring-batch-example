package com.treefinance.payment.batch.processing.sample;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author lxp
 * @date 2019/10/24 下午1:59
 * @Version 1.0
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override public Person process(Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        final Person transformedPerson = new Person(firstName,lastName);

        System.out.println("Converting (" + person + ") into (" + transformedPerson + ")");
        return transformedPerson;
    }
}
