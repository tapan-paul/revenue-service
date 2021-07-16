package au.gov.nsw.revenue.revenueservice.controller;

import au.gov.nsw.revenue.revenueservice.domain.Person;
import au.gov.nsw.revenue.revenueservice.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
/**
 * Expose rest interface to add Persons
 * @author Tapan Paul
 */
public class PersonController {

    private final RevenueService service;

    @PostMapping(value = "/add-person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addPerson(@RequestBody  Person person) {
       return service.addPerson(Optional.of(person));
    }
}
