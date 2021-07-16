package au.gov.nsw.revenue.revenueservice.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonVehicleRequest {

    private Vehicle vehicle;
    private Person person;
}
