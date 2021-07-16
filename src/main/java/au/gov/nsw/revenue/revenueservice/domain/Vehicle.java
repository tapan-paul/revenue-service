package au.gov.nsw.revenue.revenueservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Vehicle {


    //A series of numbers and/or letters that is assigned
    // to a vehicle by the relevant road traffic authority upon
    // registration of a motor vehicle. Often shortened to Rego.
    private String rego;
    private String make;
    private String model;
    private int year;

}
