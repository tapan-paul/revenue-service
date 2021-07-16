package au.gov.nsw.revenue.revenueservice.repository;

import au.gov.nsw.revenue.revenueservice.domain.Person;
import au.gov.nsw.revenue.revenueservice.domain.Vehicle;
import au.gov.nsw.revenue.revenueservice.exception.RevenueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
/**
 * Represent the back-end repository handling functions.
 */
public class PersonVehicleRepository {

    /// in place of a db - resembles the 1..N relationship person and vehicle
    // could have used  in-mem cache or in-mem db also
    private Map<Integer, Set<Vehicle>> vehiclesPerPerson;
    private Set<Person> personList; // each person is unique
    private Set<Vehicle> vehiclesList; // each vehicle is unique

    public PersonVehicleRepository() {
        vehiclesPerPerson = new HashMap<>();
        personList = new HashSet<>();
        vehiclesList = new HashSet<>();
    }

    public void addVehicleToPerson(Optional<Person> person, Optional<Vehicle> vehicle) {

        person.ifPresentOrElse( p -> {
            vehicle.ifPresentOrElse (
                    v -> { vehiclesPerPerson.computeIfAbsent(p.getId(), k->new HashSet<>()).add(v); },
                    () -> { log.error("vehicle is null");});
            }, () -> { log.error("person is null "); });
    }

    public Set<Vehicle> getVehicleList(Optional<Integer> person) {

        Set<Vehicle> vehicles = vehiclesPerPerson
                .entrySet()
                .stream()
                .filter(p -> p.getKey().equals(person.get()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(new HashSet<>());
        return vehicles;
    }

    public boolean removeVehicle(Optional<Integer> person, Optional<Vehicle> vehicle) {

        return vehiclesPerPerson
                .entrySet()
                .stream()
                .filter(p -> p.getKey().equals(person.get()))
                .map(Map.Entry::getValue)
                .findFirst().get().remove(vehicle.orElseThrow());
    }

    public boolean addPerson(Optional<Person> person) {

        Person p = person.orElseThrow(() -> new RevenueException("person empty"));
        Optional<Person> isFound = personList.stream().filter(per -> per.equals(p)).findFirst();
        if (isFound.isEmpty()) { personList.add(p); return true; };
        return false;
    }

    public boolean addVehicle(Optional<Vehicle> vehicle) {

        Vehicle v = vehicle.orElseThrow(() -> new RevenueException("vehicle empty"));
        Optional<Vehicle> isFound = vehiclesList.stream().filter(vec -> vec.equals(v)).findFirst();
        if(isFound.isEmpty()) { vehiclesList.add(v); return true; }
        return false;
    }

}
