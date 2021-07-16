package au.gov.nsw.revenue.revenueservice.controller;

import au.gov.nsw.revenue.revenueservice.domain.PersonVehicleRequest;
import au.gov.nsw.revenue.revenueservice.domain.Vehicle;
import au.gov.nsw.revenue.revenueservice.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
/**
 * Expose functionality for Vehicles and linking/unlinking to Persons
 * @author Tapan Paul
 */
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private final RevenueService service;

    @GetMapping("/")
    public @ResponseBody String getTest(){
        return "Welcome to REVENUE NSW";
    }

    @PostMapping(value = "/register-vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void registerVehicle(@RequestBody PersonVehicleRequest request) {
        service.addVehicleToPerson(Optional.of(request.getPerson()), Optional.of(request.getVehicle()));
    }

    @PostMapping(value = "/unregister-vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean unregisterVehicle(@RequestBody PersonVehicleRequest request) {
       return service.removeVehicle(Optional.of(request.getPerson().getId()), Optional.of(request.getVehicle()));
    }

    @GetMapping(value = "/get-vehicles", params = {"id"})
    public @ResponseBody ResponseEntity<Collection<Vehicle>> getAllVehiclesByPerson(Integer id) {
        Collection<Vehicle> collection = service.findAllVehiclesByPerson(Optional.of(id));
        return new ResponseEntity<Collection<Vehicle>>(collection, HttpStatus.OK);
    }

    @PostMapping(value = "/add-vehicle" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean addVehicle(@RequestBody Vehicle vehicle) {
        return service.addVehicle(Optional.of(vehicle));
    }
}
