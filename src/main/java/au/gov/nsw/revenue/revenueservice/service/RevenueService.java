package au.gov.nsw.revenue.revenueservice.service;

import au.gov.nsw.revenue.revenueservice.domain.Person;
import au.gov.nsw.revenue.revenueservice.domain.Vehicle;
import au.gov.nsw.revenue.revenueservice.repository.PersonVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
/**
 * Service layer to backend
 * @author tapan paul
 */
public class RevenueService {

    private final PersonVehicleRepository vehicleRepository;

    public Set<Vehicle> findAllVehiclesByPerson(Optional<Integer> person) {
        return vehicleRepository.getVehicleList(person);
    }

    public void addVehicleToPerson(Optional<Person>  person, Optional<Vehicle> vehicle) {
        vehicleRepository.addVehicleToPerson(person, vehicle);
    }

    public boolean removeVehicle(Optional<Integer> person, Optional<Vehicle> vehicle) {
        return vehicleRepository.removeVehicle(person, vehicle);
    }

    public boolean addPerson(Optional<Person> person) {
        return vehicleRepository.addPerson(person);
    }

    public boolean addVehicle(Optional<Vehicle> vehicle) {
        return vehicleRepository.addVehicle(vehicle);
    }
}
