package au.gov.nsw.revenue.revenueservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@EqualsAndHashCode
@ToString
/**
 * Represent sa person in the system - basic attributes added only for purpose of exercise.
 */
public class Person {

    private int id; // unique id
    private String firstName;
    private String lastName;
    private String salutation;
}
